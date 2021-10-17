package com.hackathon.inventory.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "hackathon_inventory")
public class Inventory {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "user_id")
	private Long userId;
	@Column(name = "product_id")
	private Long productId;
	@Column(name = "product_quantity")
	private Integer productQuantity;
	@Column(name = "product_price")
	private Double productPrice;
}
