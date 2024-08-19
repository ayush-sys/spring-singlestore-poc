package com.singlestore.singlestore_application.service;


import com.singlestore.singlestore_application.model.FactModel;
import com.singlestore.singlestore_application.repository.FactRepository;
import com.singlestore.singlestore_application.service.kafka.ProducerService;
import com.singlestore.singlestore_application.utils.Constants;
import com.singlestore.singlestore_application.utils.StatusMessage;
import com.singlestore.singlestore_application.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/** The application services. */

@Slf4j
@Service
public class S2ApplicationService {

    @Autowired
    private ProducerService producerService;

    @Autowired
    private Utils utils;

    @Autowired
    private FactRepository factRepository;

    public List<FactModel> getRecordsByWaitoverFlage(String waitoverFlag){
        try {
            log.info("Processing status :" + StatusMessage.SUCCESS + utils.getCurrentTimestamp());
            return factRepository.findEntriesForWaitoverFlag(waitoverFlag);
        } catch (Exception e) {
            log.error("Exception faced, while getting entries for waitover :: " + e.getMessage());
        }
        return null;
    }

    public List<FactModel> getAllRecords(){
        try {
            log.info("Processing status :" + StatusMessage.SUCCESS + utils.getCurrentTimestamp());
            return factRepository.findAll();
        } catch (Exception e) {
            log.error("Exception faced, while getting records {}", e.getMessage());
        }
        return null;
    }

    /**
     * The fetchAndProduceRecords function.
     *
     * fetch records where waitover flag = 0
     * produce these records to a kafka topic
     *
     * */
    public void fetchAndProduceRecords(){
        List<FactModel> factList = factRepository.findEntriesForWaitoverFlag("0");

        // New JSONArray for storing fact records
        JSONArray factJsonArr = new JSONArray();
        try {
            log.info("fetchAndProduceRecords :: {} started to fetch and produce records at {}", StatusMessage.PROCESSING, utils.getCurrentTimestamp());

            // New JSONObject to store records in
            // { "MSISDN":10098, "CAM_ID":"CAM098", "TRANSACTION_ID":"TX098" }

            JSONObject factJson = new JSONObject();
            for (FactModel factRecord : factList){
                factJson.put(String.valueOf(Constants.MSISDN), factRecord.getMsisdn());
                factJson.put(String.valueOf(Constants.CAM_ID), factRecord.getCamId());
                factJson.put(String.valueOf(Constants.TRANSACTION_ID), factRecord.getTransactionId());
                factJsonArr.put(factJson);
            }

            // Produce record to kafka topic
            producerService.produceRecordsToKafka("ecmpwaitover", factJsonArr.toString());

        } catch (Exception e){
            log.error("{} faced while producing records to kafka at {}", StatusMessage.ERROR, utils.getCurrentTimestamp());
        }
    }
}
