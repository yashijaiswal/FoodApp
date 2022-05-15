package com.example.foodapp;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableTransactionManagement
public class FoodappApplication {

	@Value("${http.timeout.in.seconds}")
	private Integer timeoutInSeconds;
	
	public static void main(String[] args) {
		SpringApplication.run(FoodappApplication.class, args);
	}
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {

		Duration duration = Duration.ofSeconds(timeoutInSeconds);
		return restTemplateBuilder
				.setConnectTimeout(duration)
				.setReadTimeout(duration)
				.build();
	}
}
