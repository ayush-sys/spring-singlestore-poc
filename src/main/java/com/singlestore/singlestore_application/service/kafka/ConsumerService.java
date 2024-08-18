package com.singlestore.singlestore_application.service.kafka;


import com.singlestore.singlestore_application.config.AppConfig;
import com.singlestore.singlestore_application.utils.Status;
import com.singlestore.singlestore_application.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/** This is Kafka consumer service. */

@Slf4j
@Service
public class ConsumerService {

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private Utils utils;

    String message = null;

    private final Object lock = new Object();

    @KafkaListener(topics = "${kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(String message) {
        // message processing logic here
        try {
            log.info("{} message received from kafka at {}", Status.PROCESSING, utils.getCurrentTimestamp());
            synchronized (lock){
                this.message = message;
            }
        } catch (Exception e){
            log.error("Message listen successfully at {}", utils.getCurrentTimestamp());
        }
    }

    private ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    private Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, appConfig.getKafkaBootstrapServer());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, appConfig.getKafkaConsumerGrpId());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return props;
    }
}
