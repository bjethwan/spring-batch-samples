package com.bjethwan;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@EnableBatchProcessing
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class Main {
	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
}

//mvn clean package
//java -jar target\spring-batch-sample0-jdbc-with-job-params-1.0.jar msg=day1
//java -jar target\spring-batch-sample0-jdbc-with-job-params-1.0.jar msg=day2
//java -jar target\spring-batch-sample0-jdbc-with-job-params-1.0.jar msg=day3

