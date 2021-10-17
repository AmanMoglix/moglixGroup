package com.hackathon.catalog.model.co;

import lombok.Data;

@Data
public class TransactionDetailsCO {
	private Long id;
	private Long productId;
	private Integer quantity;
	private Double productPrice;
	private Double totalPrice;
	private Long status;
}
