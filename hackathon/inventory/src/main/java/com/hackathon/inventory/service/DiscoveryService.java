package com.hackathon.inventory.service;

import com.hackathon.inventory.model.CurrentUser;
import com.hackathon.inventory.model.dto.discovery.CatalogDiscoveryDTO;

public interface DiscoveryService {
	public CatalogDiscoveryDTO fetchByProductId(Long productId, CurrentUser currentUser);
}
