package com.singlestore.singlestore_application.kafka.services;


import com.singlestore.singlestore_application.config.AppConfig;
import com.singlestore.singlestore_application.utils.StatusMessage;
import com.singlestore.singlestore_application.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/** This is Kafka consumer service. */

@Slf4j
@Service
@EnableKafka
public class ConsumerService {

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private Utils utils;

    String message = null;

    private final Object lock = new Object();

    @KafkaListener(topics = "${kafka.consumer.topic.name}", groupId = "${kafka.consumer.group-id}")
    public void onReceive(String message) {
        // message processing logic here
        try {
            log.info("{} message received from kafka at {}", StatusMessage.PROCESSING, utils.getCurrentTimestamp());
            synchronized (lock){
                log.info("{} :: messages received at {}, synchronizing messages!", StatusMessage.SUCCESS, utils.getCurrentTimestamp());
                this.message = message;     // synchronized the messages received from kafka
            }
        } catch (Exception e){
            log.error("{} faced while consuming records at {} | Exception :: {}", StatusMessage.ERROR, utils.getCurrentTimestamp(), e.getMessage());
        }
    }

}
