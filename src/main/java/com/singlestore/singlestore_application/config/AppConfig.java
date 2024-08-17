package com.singlestore.singlestore_application.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/** The application config. */

@Data
@Configuration
public class AppConfig {

    @Value("${producer.topic}")
    private String producerTopic;

    @Value("${server.port}")
    private String serverPort;

    @Value("${timezone}")
    private String timezone;

    @Value("${time.pattern}")
    private String timePattern;

}
