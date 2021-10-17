package com.hackathon.supplier.model.dto.discovery;

import lombok.Data;

@Data
public class CatalogDiscoveryDTO {
	private Long id;
	private Long categoryId;
	private String productName;
	private String productDescription;
	private String productImage;
}
