package com.hackathon.catalog.model.co;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class TransactionHeadCO {
	private Long id;

	private Long supplierId;

	private Long inventoryId;

	private String type;

	private String billNo;

	private String remarks;

	private Long status;

	private Date billDate;

	private Double totalTaxableAmount;

	private Double grandTotal;

	private List<TransactionDetailsCO> txnDetailsCO;
}
