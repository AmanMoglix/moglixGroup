package com.hackathon.online.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
@Data
@Entity
@Table(name="hackathon_cart")
public class Cart {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private Long userId;
	private Long inventoryId;
	private Long productId;
	private Integer productQuantity;
	private Double totalPrice;


}
