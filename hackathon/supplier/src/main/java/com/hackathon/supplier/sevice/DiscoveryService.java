package com.hackathon.supplier.sevice;

import java.util.List;

import com.hackathon.supplier.model.CurrentUser;
import com.hackathon.supplier.model.dto.discovery.CatalogDiscoveryDTO;
import com.hackathon.supplier.model.dto.discovery.InventoryDiscoveryDTO;
import com.hackathon.supplier.model.dto.discovery.OrderDiscoveryDTO;

public interface DiscoveryService {
	public List<CatalogDiscoveryDTO> fetchCatalogs(CurrentUser currentUser);
	
	public CatalogDiscoveryDTO fectchByProductId(Long productId,CurrentUser currentUser);

	public List<OrderDiscoveryDTO> fetchAssignedOrders(CurrentUser currentUser);

	public List<InventoryDiscoveryDTO> fetcInventoris(CurrentUser currentUser);
}
