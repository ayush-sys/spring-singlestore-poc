package com.singlestore.singlestore_application.kafka.producer;

import com.singlestore.singlestore_application.config.AppConfig;
import com.singlestore.singlestore_application.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.stereotype.Service;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.util.*;


@Slf4j
@Service
/** The Kafka Producer Service Implementation. */
public class ProducerServiceImpl implements IProducerService {

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private Utils utils;

    @Autowired
    @Lazy
    private KafkaTemplate<String, String> kafkaTemplate;

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, appConfig.getKafkaBootstrapServer());
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Override
    public void publishMessage(String topic, String message) {
        log.info("publishMessage :: Message received :: {} | producing to topic :: {} at {}", message, topic, utils.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
        try {
            kafkaTemplate.send(topic, message);
            log.info("publishMessage :: Message successfully produced to topic :: {} at {}", topic, utils.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
        } catch (Exception e) {
            log.error("publishMessage :: Error faced at {} :: Exception :: {}", utils.getCurrentTime("yyyy-MM-dd HH:mm:ss"), e.getMessage(), e);
        }
    }

    @Override
    public void createTopicIfNotExists() {
        log.info("createTopicIfNotExists :: Processing started at {}", utils.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
        Properties configs = new Properties();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, appConfig.getKafkaBootstrapServer());

        try (AdminClient adminClient = AdminClient.create(configs)) {
            // Split the topic names by comma and trim any whitespace
            String[] topics = Arrays.stream(appConfig.getKafkaProducerTopicList().split(","))
                    .map(String::trim)
                    .toArray(String[]::new);

            // Fetch the existing topics
            Set<String> existingTopics = new HashSet<>(adminClient.listTopics().names().get());

            // Create a list to hold new topics
            List<NewTopic> newTopics = new ArrayList<>();

            // Iterate over each topic and prepare new topics if not exists
            for (String topicName : topics) {
                if (!existingTopics.contains(topicName)) {
                    newTopics.add(new NewTopic(topicName, appConfig.getTopicPartitions(), appConfig.getTopicReplicas()));
                    log.info("{} :: topic is queued for creation at {}", topicName, utils.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
                } else {
                    log.info("{} :: Topic already exists", topicName);
                }
            }

            // Create the new topics in one batch operation
            if (!newTopics.isEmpty()) {
                adminClient.createTopics(newTopics).all().get();
                newTopics.forEach(topic -> log.info("{} :: topic successfully created at {}", topic.name(), utils.getCurrentTime("yyyy-MM-dd HH:mm:ss")));
            }
        } catch (Exception e) {
            log.error("createTopicIfNotExists :: Error faced at {}", utils.getCurrentTime("yyyy-MM-dd HH:mm:ss"), e);
        }
    }

}