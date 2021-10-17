package com.hackathon.inventory.service;

import java.util.List;

import com.hackathon.inventory.domain.Inventory;
import com.hackathon.inventory.model.CurrentUser;
import com.hackathon.inventory.model.dto.InventoryDTO;

public interface InventoryService {
	public Inventory saveOrUpdate(Inventory inventory, CurrentUser currentUser);

	public InventoryDTO getById(Long inventoryId, CurrentUser currentUser);

	public List<InventoryDTO> list(CurrentUser currentUser);

	public void delete(Long inventoryId,CurrentUser currentUser);
}
