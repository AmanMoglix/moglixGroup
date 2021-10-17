package com.hackathon.supplier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@EnableAsync
public class SupplierApplication {

	public static void main(String[] args) {
		SpringApplication.run(SupplierApplication.class, args);
	}
	@Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
