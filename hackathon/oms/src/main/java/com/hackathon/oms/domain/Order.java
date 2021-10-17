package com.hackathon.oms.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "hackathon_order")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "user_id")
	private Long userId;
	@Column(name = "product_id")
	private Long productId;
	@Column(name = "product_quantity")
	private Integer productQuantity;
	@Column(name = "total_price")
	private Double totalPrice;
	@Column(name = "grand_total")
	private Double grandTotal;
}
