package com.singlestore.singlestore_application;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication		// Enable Configuration, EnableAutoConfiguration and ComponentScan
@OpenAPIDefinition(info = @Info(title = "Singlestore application", version = "1.0", description = "Run data pipeline"))
public class SinglestoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(SinglestoreApplication.class, args);
	}

}
