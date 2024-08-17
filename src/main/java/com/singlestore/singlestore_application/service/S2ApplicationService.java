package com.singlestore.singlestore_application.service;


import com.singlestore.singlestore_application.model.FactModel;
import com.singlestore.singlestore_application.repository.FactRepository;
import com.singlestore.singlestore_application.utils.Status;
import com.singlestore.singlestore_application.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/** The application services. */

@Slf4j
@Service
public class S2ApplicationService {

    @Autowired
    private Utils utils;

    @Autowired
    private FactRepository factRepository;

    public List<FactModel> getRecordsByWaitoverFlage(String waitoverFlag){
        try {
            log.info("Processing status :" + Status.SUCCESS + utils.getCurrentTimestamp());
            return factRepository.findEntriesForWaitoverFlag(waitoverFlag);
        } catch (Exception e) {
            log.error("Exception faced, while getting entries for waitover :: " + e.getMessage());
        }
        return null;
    }

    public List<FactModel> getAllRecords(){
        try {
            log.info("Processing status :" + Status.SUCCESS + utils.getCurrentTimestamp());
            return factRepository.findAll();
        } catch (Exception e) {
            log.error("Exception faced, while getting records " + e.getMessage());
        }
        return null;
    }
}
