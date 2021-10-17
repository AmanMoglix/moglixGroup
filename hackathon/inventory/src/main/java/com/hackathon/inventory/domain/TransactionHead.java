package com.hackathon.inventory.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="transaction_head")
public class TransactionHead {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	/*supplierId its an userId*/
	@Column(name="supplier_id")
	private Long supplierId;
	
	@Column(name="inventory_id")
	private Long inventoryId;
	
	@Column(name="type")
	private String type;
	
	@Column(name="bill_no")
	private String billNo;
	
	@Column(name="remarks")
	private String remarks;
	
	@Column(name="status")
	private Long status;
	
	@Column(name="bill_date")
	private Date billDate;
	
	@Column(name="total_taxble_amount")
	private double totalTaxableAmount;
	
	@Column(name="grand_total")
	private double grandTotal;
	
	@OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinColumn(name = "txn_head_id")
	private List<TransactionDetails> txnDetailsCO;

}
