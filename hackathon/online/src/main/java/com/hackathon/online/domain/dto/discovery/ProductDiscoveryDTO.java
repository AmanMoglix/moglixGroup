package com.hackathon.online.domain.dto.discovery;

import lombok.Data;

@Data
public class ProductDiscoveryDTO {
	private Long productId;
	private Long categoryId;
	private String productName;
	private String productDescription;
	private String productImage;
}
