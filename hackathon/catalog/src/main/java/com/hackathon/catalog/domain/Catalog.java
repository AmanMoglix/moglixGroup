package com.hackathon.catalog.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "hackathon_catalog")
public class Catalog {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "category_id")
	private Long categoryId;
	@Column(name = "product_name")
	private String productName;
	@Column(name = "product_description")
	private String productDescription;
	@Column(name = "product_image")
	private String productImage;

}
