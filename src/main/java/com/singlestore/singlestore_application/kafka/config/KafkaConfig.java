package com.singlestore.singlestore_application.kafka.config;


import com.singlestore.singlestore_application.config.AppConfig;
import com.singlestore.singlestore_application.utils.StatusMessage;
import com.singlestore.singlestore_application.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;

import java.util.*;
import java.util.concurrent.ExecutionException;

/** The KafkaConfig class. */

@Slf4j
@Configuration
@EnableKafka
public class KafkaConfig {

    private final AppConfig appConfig;

    private final Utils utils;

    @Autowired
    public KafkaConfig(AppConfig appConfig, Utils utils) {
        this.appConfig = appConfig;
        this.utils = utils;
    }

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, appConfig.getKafkaBootstrapServer());
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

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

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    /**
     * The createTopicIfNotExists function.
     *
     * @throws ExecutionException - the execution exception
     * @throws InterruptedException - the interrupted exception
     */
    public void createTopicIfNotExists() throws ExecutionException, InterruptedException {
        log.info("createTopicIfNotExists :: {} started at {}", StatusMessage.PROCESSING, utils.getCurrentTimestamp());
        Properties configs = new Properties();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, appConfig.getKafkaBootstrapServer());

        try (AdminClient adminClient = AdminClient.create(configs)) {
            // Split the topic names by comma
            String[] topics = appConfig.getKafkaProducerTopicList().split(",");

            // Fetch the existing topics
            Set<String> existingTopics = adminClient.listTopics().names().get();

            // Iterate over each topic and create new topic if not exists
            for (String topicName : topics) {
                topicName = topicName.trim();  // Trim any whitespace around the topic name
                if (!existingTopics.contains(topicName)) {
                    NewTopic newTopic = new NewTopic(topicName, appConfig.getTopicPartitions(), appConfig.getTopicReplicas());
                    adminClient.createTopics(Collections.singletonList(newTopic)).all().get();
                    log.info("{} :: New Topic {} created at {}", StatusMessage.SUCCESS, topicName, utils.getCurrentTimestamp());
                } else {
                    log.info("{} :: Topic already exists: {}", StatusMessage.FAILED, topicName);
                }
            }
        } catch (Exception e) {
            log.error("createTopicIfNotExists :: {} faced at {}", StatusMessage.ERROR, utils.getCurrentTimestamp(), e);
        }
    }

}
