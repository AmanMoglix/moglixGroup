package com.hackathon.catalog.model.dto.discovery;

import lombok.Data;

@Data
public class InventoryDiscoveryDTO {
	private Long inventoryId;

	private Long userId;

	private Long productId;

	private Integer productQuantity;

	private Double productPrice;
}
