package com.hackathon.inventory.model.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hackathon.inventory.domain.TransactionDetails;
import com.hackathon.inventory.model.dto.discovery.SupplierDiscoveryDTO;

import lombok.Data;
@Data
public class TransactionHeadDTO {
	private Long id;

	private Long supplierId;
	
	private Long inventoryId;

	private String type;

	private String billNo;

	private String remarks;

	private Long status;

	private Date billDate;

	private double totalTaxableAmount;

	private double grandTotal;

	private List<TransactionDetails> transactionDetails;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private SupplierDiscoveryDTO supplier;
}

