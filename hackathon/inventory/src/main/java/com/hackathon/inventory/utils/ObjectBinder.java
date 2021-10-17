package com.hackathon.inventory.utils;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hackathon.inventory.domain.Inventory;
import com.hackathon.inventory.domain.Stock;
import com.hackathon.inventory.domain.TransactionHead;
import com.hackathon.inventory.model.dto.InventoryDTO;
import com.hackathon.inventory.model.dto.StockDTO;
import com.hackathon.inventory.model.dto.TransactionHeadDTO;

@Component
public class ObjectBinder {

	@Autowired
	private ModelMapper mapper;

	public InventoryDTO bindInventory(Inventory inventory) {
		if (inventory == null)
			return null;
		return this.mapper.map(inventory, InventoryDTO.class);
	}

	public List<InventoryDTO> bindInventories(List<Inventory> inventories) {
		if (inventories == null || inventories.isEmpty())
			return new ArrayList<>();
		List<InventoryDTO> inventoryDTOs = new ArrayList<InventoryDTO>();
		inventories.forEach(it -> {
			if (it != null)
				inventoryDTOs.add(bindInventory(it));
		});
		return inventoryDTOs;
	}

	public StockDTO bindStock(Stock stock) {
		if (stock == null)
			return null;
		return this.mapper.map(stock, StockDTO.class);
	}

	public List<StockDTO> bindStocks(List<Stock> stocks) {
		if (stocks == null || stocks.isEmpty())
			return new ArrayList<>();
		List<StockDTO> stockDTOs = new ArrayList<StockDTO>();
		stocks.forEach(it -> {
			if (it != null)
				stockDTOs.add(bindStock(it));
		});
		return stockDTOs;
	}

	public TransactionHeadDTO bindTxnHead(TransactionHead txnHead) {
		if (txnHead == null)
			return null;
		return this.mapper.map(txnHead, TransactionHeadDTO.class);
	}

	public List<TransactionHeadDTO> bindTxnHeads(List<TransactionHead> transactionHeads) {
		if (transactionHeads == null || transactionHeads.isEmpty())
			return new ArrayList<>();
		List<TransactionHeadDTO> tHeadDTOs = new ArrayList<TransactionHeadDTO>();
		transactionHeads.forEach(it -> {
			if (it != null)
				tHeadDTOs.add(bindTxnHead(it));
		});
		return tHeadDTOs;
	}
}
