package com.singlestore.singlestore_application.kafka.producer;

/** The IProducerService interface. */

public interface IProducerService {

    /**
     * The publishMessage function.
     *
     * @param topic - the kafka topic name
     * @param message - the publishing message
     * */
    void publishMessage(String topic, String message);

    /**
     * The createTopicIfNotExists function.
     *
     * creates kafka topic on application boot
     */
    void createTopicIfNotExists();

}