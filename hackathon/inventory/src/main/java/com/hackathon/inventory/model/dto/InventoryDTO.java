package com.hackathon.inventory.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hackathon.inventory.model.dto.discovery.CatalogDiscoveryDTO;

import lombok.Data;
@Data
public class InventoryDTO {
	private Long inventoryId;
	private Long userId;
	private Long productId;
	private Integer productQuantity;
	private Double productPrice;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private CatalogDiscoveryDTO catalog;
}
