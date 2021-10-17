package com.hackathon.supplier.model.dto.discovery;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
public class InventoryDiscoveryDTO {
	private Long id;
	private Long productId;
	/* private Long inventoryId; */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private CatalogDiscoveryDTO catalog;
	private Long userId;
	private Integer quantity;
	private Double productPrice;
}
