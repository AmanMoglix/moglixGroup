package com.hackathon.catalog.model.co;

import lombok.Data;

@Data
public class StockCO {
	private Long productId;
	private Integer quantity;
	private Double price;
	private Double tax;
	private Double grandTotal;
}
