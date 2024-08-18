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

    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaBootstrapServer;

    @Value("${spring.kafka.producer.key-serializer}")
    private String kafkaProducerSerializerKey;

    @Value("${spring.kafka.producer.value-serializer}")
    private String kafkaProducerSerializerValue;

    @Value("${spring.kafka.producer.properties.schema.registry.url}")
    private String kafkaProducerSchema;

}
