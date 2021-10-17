package com.hackathon.catalog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;
@Data
@Component("config")
public class ApplicationConfig {

	@Value("${hackathon.moglix.auth.serviceId}")
	private String authServiceId;
	
	@Value("${hackathon.moglix.inventory.serviceId}")
	private String inventoryServiceId;

}
