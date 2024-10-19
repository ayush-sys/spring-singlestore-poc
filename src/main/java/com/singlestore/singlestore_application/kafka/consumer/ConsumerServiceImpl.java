package com.singlestore.singlestore_application.kafka.consumer;


import com.singlestore.singlestore_application.config.AppConfig;
import com.singlestore.singlestore_application.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@Service
/** The Kafka Consumer Service Implementation. */
public class ConsumerServiceImpl implements IConsumerService {

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private Utils utils;

    private final Object lock = new Object();

    private String message = null;

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, appConfig.getKafkaBootstrapServer());
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, appConfig.getKafkaConsumerGrpId());
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(configProps);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Override
    @KafkaListener(topics = "${kafka.consumer.topic.name}", groupId = "${kafka.consumer.group-id}")
    public void onReceive(String message) {
        // message processing logic here
        try {
            synchronized (lock) {
                log.info("Successfully :: Message received at {}, synchronizing messages!", utils.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
                this.message = message; // Synchronized access to the message
                log.info("Message received :: {}", message);
            }
        } catch (Exception e) {
            log.error("Exception faced while consuming records at {} | Exception :: {}", utils.getCurrentTime("yyyy-MM-dd HH:mm:ss"), e.getMessage());
        }
    }

}