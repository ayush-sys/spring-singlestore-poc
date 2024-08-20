package com.singlestore.singlestore_application.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/** The application config. */

@Data
@Configuration
public class AppConfig {

    @Value("${server.port}")
    private String serverPort;

    @Value("${timezone}")
    private String timezone;

    @Value("${time.pattern}")
    private String timePattern;

    @Value("${kafka.producer.topic.list}")
    private String kafkaProducerTopicList;

    @Value("${kafka.topic.partitions}")
    private int topicPartitions;

    @Value("${kafka.topic.replicas}")
    private short topicReplicas;

    @Value("${kafka.bootstrap-servers}")
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

}
