package com.singlestore.singlestore_application.kafka.consumer;

/** The IConsumerService interface. */

public interface IConsumerService {

    /**
     * The onReceive function.
     *
     * @param message - the message received from kafka topic
     * */
    void onReceive(String message);

}