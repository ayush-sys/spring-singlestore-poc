package com.singlestore.singlestore_application.kafka.services;

import com.singlestore.singlestore_application.config.AppConfig;
import com.singlestore.singlestore_application.utils.StatusMessage;
import com.singlestore.singlestore_application.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/** This is Kafka consumer service. */

@Slf4j
@Service
public class ConsumerService {

    private final AppConfig appConfig;

    private final Utils utils;

    private final Object lock = new Object();

    private String message = null;

    @Autowired
    public ConsumerService(AppConfig appConfig, Utils utils) {
        this.appConfig = appConfig;
        this.utils = utils;
    }

    @KafkaListener(topics = "${kafka.consumer.topic.name}", groupId = "${kafka.consumer.group-id}")
    public void onReceive(String message) {
        // message processing logic here
        try {
            log.info("{} message received from Kafka at {}", StatusMessage.PROCESSING, utils.getCurrentTimestamp());
            synchronized (lock) {
                log.info("{} :: Message received at {}, synchronizing messages!", StatusMessage.SUCCESS, utils.getCurrentTimestamp());
                this.message = message; // Synchronized access to the message
            }
        } catch (Exception e) {
            log.error("{} encountered while consuming records at {} | Exception :: {}", StatusMessage.ERROR, utils.getCurrentTimestamp(), e.getMessage(), e);
        }
    }

}
