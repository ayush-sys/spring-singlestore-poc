package com.singlestore.singlestore_application.kafka.services;

import com.singlestore.singlestore_application.kafka.config.KafkaConfig;
import com.singlestore.singlestore_application.utils.StatusMessage;
import com.singlestore.singlestore_application.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/** This is Kafka producer service. */

@Slf4j
@Service
public class ProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final Utils utils;

    @Autowired
    public ProducerService(KafkaTemplate<String, String> kafkaTemplate, Utils utils) {
        this.kafkaTemplate = kafkaTemplate;
        this.utils = utils;
    }

    /**
     * The publishMessage function.
     *
     * @param topicName - the kafka topic name.
     * @param message -  the message to be sent to topic.
     */
    public void publishMessage(String topicName, String message) {
        log.info("publishMessage :: Message received :: {} | producing to topic :: {} | time :: {}", message, topicName, utils.getCurrentTimestamp());
        try {
            kafkaTemplate.send(topicName, message);
            log.info("publishMessage :: Message successfully produced to topic :: {} | time :: {}", topicName, utils.getCurrentTimestamp());
        } catch (Exception e) {
            log.error("publishMessage :: {} faced at {} :: Exception :: {}", StatusMessage.ERROR, utils.getCurrentTimestamp(), e.getMessage(), e);
        }
    }

}
