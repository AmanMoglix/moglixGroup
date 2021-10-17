package com.hackathon.inventory.service;

import java.util.List;

import com.hackathon.inventory.domain.Stock;
import com.hackathon.inventory.model.CurrentUser;
import com.hackathon.inventory.model.dto.StockDTO;

public interface StockService {
	public StockDTO saveOrUpdate(Stock stock, CurrentUser currentUser);

	public StockDTO getById(Long stockId, CurrentUser currentUser);

	public List<StockDTO> list(CurrentUser currentUser);

	public void delete(Long stockId, CurrentUser currentUser);

	public Stock findByProductIdAndInventoryId(Long product, Long inventoryId,CurrentUser currentUser);

	public List<StockDTO> getStocksByCurrentUser(CurrentUser currentUser);
}
