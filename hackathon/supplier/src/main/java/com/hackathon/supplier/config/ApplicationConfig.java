package com.hackathon.supplier.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationConfig {
	@Value("${hackathon.moglix.catalog.serviceId}")
	private String catalogServiceId;
	@Value("${hackathon.moglix.inventory.serviceId}")
	private String inventoryServiceId;
	@Value("${hackathon.moglix.oms.serviceId}")
	private String omsServiceId;

	public String getCatalogServiceId() {
		return catalogServiceId;
	}

	public void setCatalogServiceId(String catalogServiceId) {
		this.catalogServiceId = catalogServiceId;
	}

	public String getInventoryServiceId() {
		return inventoryServiceId;
	}

	public void setInventoryServiceId(String inventoryServiceId) {
		this.inventoryServiceId = inventoryServiceId;
	}

	public String getOmsServiceId() {
		return omsServiceId;
	}

	public void setOmsServiceId(String orderServiceId) {
		this.omsServiceId = orderServiceId;
	}

}
