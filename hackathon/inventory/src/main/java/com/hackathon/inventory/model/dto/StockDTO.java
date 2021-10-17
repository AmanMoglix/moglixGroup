package com.hackathon.inventory.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hackathon.inventory.model.dto.discovery.CatalogDiscoveryDTO;

import lombok.Data;
@Data
public class StockDTO {
	private Long id;
	private Long productId;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private CatalogDiscoveryDTO catalog;
	private Long inventoryId;
	private Integer quantity;
	private Double productPrice;


}
