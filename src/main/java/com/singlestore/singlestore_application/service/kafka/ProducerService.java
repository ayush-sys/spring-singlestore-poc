package com.singlestore.singlestore_application.service.kafka;


import com.singlestore.singlestore_application.config.AppConfig;
import com.singlestore.singlestore_application.utils.Status;
import com.singlestore.singlestore_application.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/** This is Kafka producer service. */

@Slf4j
@Service
public class ProducerService {

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private Utils utils;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public ProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * The createTopicIfNotExists function.
     */
    public void createTopicIfNotExists() throws ExecutionException, InterruptedException {
        log.info("createTopicIfNotExists :: {} started at {}", Status.PROCESSING, utils.getCurrentTimestamp());
        Properties configs = new Properties();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, appConfig.getKafkaBootstrapServer());

        try (AdminClient adminClient = AdminClient.create(configs)) {
            // Split the topic names by comma
            String[] topics = appConfig.getKafkaProducerTopicList().split(",");

            // Fetch the existing topics
            Set<String> existingTopics = adminClient.listTopics().names().get();

            // Iterate over each topic and create new topic
            for (String topicName : topics) {
                topicName = topicName.trim();  // Trim any whitespace around the topic name
                if (!existingTopics.contains(topicName)) {
                    NewTopic newTopic = new NewTopic(topicName, appConfig.getTopicPartitions(), appConfig.getTopicReplicas());
                    adminClient.createTopics(Collections.singletonList(newTopic)).all().get();
                    log.info("{} :: New Topic {} created at {}", Status.SUCCESS, topicName, utils.getCurrentTimestamp());
                } else {
                    log.info("{} :: Topic exists already : {}", Status.FAILED, topicName);
                }
            }
        } catch (Exception e) {
            log.error("createTopicIfNotExists :: {} faced at {}", Status.ERROR, utils.getCurrentTimestamp(), e);
        }
    }


    /**
     * The produceRecordsToKafka function.
     *
     * @param topicName - the kafka topic name.
     * @param message -  the message to be sent to topic.
     */
    public void produceRecordsToKafka(String topicName, String message) {
        log.info("produceRecordsToKafka :: Message received and producing to topic :: {}", topicName);
        try {
            kafkaTemplate.send(topicName, message);
        } catch (Exception e){
            log.error("produceRecordsToKafka :: {} faced at {}", Status.ERROR, utils.getCurrentTimestamp());
        }
    }
}
