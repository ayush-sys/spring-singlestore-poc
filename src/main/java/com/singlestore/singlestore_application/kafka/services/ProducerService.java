package com.singlestore.singlestore_application.kafka.services;


import com.singlestore.singlestore_application.kafka.config.KafkaConfig;
import com.singlestore.singlestore_application.utils.StatusMessage;
import com.singlestore.singlestore_application.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/** This is Kafka producer service. */

@Slf4j
@Service
@EnableKafka
public class ProducerService {

    @Autowired
    private KafkaConfig kafkaConfig;

    @Autowired
    private Utils utils;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public ProducerService(KafkaTemplate<String, String> kafkaTemplate) { this.kafkaTemplate = kafkaTemplate; }

    /**
     * The kafkaTemplate function.
     *
     * @return KafkaTemplate - the kafka template
     * */
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() { return new KafkaTemplate<>(kafkaConfig.producerFactory()); }

    /**
     * The publishMessage function.
     *
     * @param topicName - the kafka topic name.
     * @param message -  the message to be sent to topic.
     */
    public void publishMessage(String topicName, String message) {
        log.info("produceRecordsToKafka :: Message received :: {} | producing to topic :: {} | time :: {}", message, topicName, utils.getCurrentTimestamp());
        try {
            kafkaTemplate.send(topicName, message);
        } catch (Exception e){
            log.error("produceRecordsToKafka :: {} faced at {} :: Exception :: {}", StatusMessage.ERROR, utils.getCurrentTimestamp(), e.getMessage());
        }
    }

}
