package com.hackathon.inventory.model.dto.discovery;

import lombok.Data;

@Data
public class SupplierDiscoveryDTO {
	private Long id;
	private String supplierName;
	private String supplierContact;
	private String supplierEmail;
	private String supplierAddress;
}
