package com.hackathon.supplier.model.dto.discovery;

import lombok.Data;

@Data
public class OrderDiscoveryDTO {
	private Long id;
	private Long userId;
	private Long productId;
	private Integer productQuantity;
	private Double totalPrice;
	private Double grandTotal;
}
