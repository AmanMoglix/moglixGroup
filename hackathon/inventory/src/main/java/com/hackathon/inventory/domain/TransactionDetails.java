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
@Table(name = "transaction_details")
public class TransactionDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "txn_head_id")
	private Long transactionHeadId;
	@Column(name = "product_id")
	private Long productId;
	@Column(name = "quantity")
	private Integer quantity;
	@Column(name = "product_price")
	private Double productPrice;
	@Column(name = "grand_total_price")
	private Double totalPrice;
	@Column(name="status")
	private Long status;
}
