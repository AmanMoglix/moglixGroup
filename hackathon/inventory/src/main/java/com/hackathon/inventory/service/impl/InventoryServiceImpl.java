package com.hackathon.inventory.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.hackathon.inventory.domain.Inventory;
import com.hackathon.inventory.model.CurrentUser;
import com.hackathon.inventory.model.dto.InventoryDTO;
import com.hackathon.inventory.model.dto.discovery.CatalogDiscoveryDTO;
import com.hackathon.inventory.repository.InventoryRepository;
import com.hackathon.inventory.service.DiscoveryService;
import com.hackathon.inventory.service.InventoryService;
import com.hackathon.inventory.utils.ObjectBinder;
import com.hackathon.inventory.utils.exception.NotFoundException;

@Service
@Transactional
public class InventoryServiceImpl implements InventoryService {
	private static final Logger logger = LoggerFactory.getLogger(InventoryServiceImpl.class);
	@Autowired
	private InventoryRepository inventoryRepository;
	@Autowired
	private DiscoveryService discoveryService;
	@Autowired
	private ObjectBinder objectBinder;

	@Override
	public Inventory saveOrUpdate(Inventory inventory, CurrentUser currentUser) {
		if (inventory.getId() == null) {
			logger.info(" Entity Going to persist user by '{}'", new Gson().toJson(currentUser));
			return this.inventoryRepository.save(inventory);

		} else if (inventory.getId() != null) {
			logger.info(" Entity Going to update user by '{}'", new Gson().toJson(currentUser));

			return this.inventoryRepository.save(inventory);
		}
		return null;
	}

	@Override
	public InventoryDTO getById(Long inventoryId, CurrentUser currentUser) {
		logger.info("Getting Entity  user by '{}'", new Gson().toJson(currentUser));

		Inventory inventory = this.inventoryRepository.getById(inventoryId);
		if (inventory == null)
			throw new NotFoundException("Inventory not found by " + inventoryId);

		InventoryDTO inverInventory = this.objectBinder.bindInventory(inventory);
		inverInventory.setCatalog(getCatalogByProductId(inventory.getProductId(), currentUser));

		return inverInventory;
	}

	@Override
	public List<InventoryDTO> list(CurrentUser currentUser) {
	List<InventoryDTO> inventoryList=this.objectBinder.bindInventories(this.inventoryRepository.findAll());
		inventoryList.forEach(it->{
			it.setCatalog(getCatalogByProductId(it.getProductId(), currentUser));
		});
	return inventoryList;
	}

	@Override
	public void delete(Long inventoryId, CurrentUser currentUser) {
		// TODO Auto-generated method stub

	}

	private CatalogDiscoveryDTO getCatalogByProductId(Long productId, CurrentUser currentUser) {
		CatalogDiscoveryDTO catalogDTO = this.discoveryService.fetchByProductId(productId, currentUser);
		if (catalogDTO == null) {
			throw new NotFoundException("Product not found by " + productId);
		}
		logger.info("Getting product by productId :"+catalogDTO);
		return catalogDTO;
	}

}
