package com.singlestore.singlestore_application.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


/** The Application configuration. */
@Data
@Configuration
public class AppConfig {

    @Value("${application.time-zone}")
    private String timeZone;

    @Value("${application.time-format}")
    private String timePattern;

    @Value("${kafka.producer.topic.list}")
    private String kafkaProducerTopicList;

    @Value("${kafka.topic.partitions}")
    private int topicPartitions;

    @Value("${kafka.topic.replicas}")
    private short topicReplicas;

    @Value("${kafka.bootstrap.servers}")
    private String kafkaBootstrapServer;

    @Value("${kafka.producer.properties.schema.registry.url}")
    private String kafkaProducerSchema;

    @Value("${kafka.consumer.topic.name}")
    private String kafkaConsumerTopic;

    @Value("${kafka.consumer.group-id}")
    private String kafkaConsumerGrpId;

    @Value("${kafka.consumer.auto-offset-reset}")
    private String kafkaConsumerOffsetReset;

    @Value("${kafka.listener.missing-topics-fatal}")
    private String kafkaListenerMissingTopics;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
