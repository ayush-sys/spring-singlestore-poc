package com.singlestore.singlestore_application;

import com.singlestore.singlestore_application.kafka.producer.IProducerService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.concurrent.ExecutionException;


@SpringBootApplication		// Enable Configuration, EnableAutoConfiguration and ComponentScan
@OpenAPIDefinition(info = @Info(title = "Data application", version = "1.0", description = "Run data pipeline"))
public class SinglestoreApplication {

	@Autowired
	private IProducerService producerService;

	public static void main(String[] args) {
		SpringApplication.run(SinglestoreApplication.class, args);
	}


	/**
	 * The postInitialization function.
	 *
	 * Initializes the services post application beans are created
	 * */
	@PostConstruct
	public void postConstruct() throws ExecutionException, InterruptedException {
		producerService.createTopicIfNotExists();
	}

}
