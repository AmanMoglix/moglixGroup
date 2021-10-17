package com.hackathon.supplier.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hackathon.supplier.model.CurrentUser;
import com.hackathon.supplier.model.dto.discovery.CatalogDiscoveryDTO;
import com.hackathon.supplier.model.dto.discovery.InventoryDiscoveryDTO;
import com.hackathon.supplier.sevice.DiscoveryService;

@RestController
@RequestMapping(value="/supplier")
public class SupplierController {
	
	@Autowired
	private DiscoveryService discoveryService;
	
	@RequestMapping(value = "", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public List<CatalogDiscoveryDTO> list(@RequestHeader("X_AUTHORITY") String authority, @RequestHeader("X_USER_ID") String userId,
			@RequestHeader("X_USERNAME") String username, @RequestHeader("X_LOCATION") String location) {

        CurrentUser currentUser = CurrentUser.getInstance(userId, username, authority, location);

		return this.discoveryService.fetchCatalogs(currentUser);
	}				
	@RequestMapping(value = "/stock", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public List<InventoryDiscoveryDTO> stockList(@RequestHeader("X_AUTHORITY") String authority, @RequestHeader("X_USER_ID") String userId,
			@RequestHeader("X_USERNAME") String username, @RequestHeader("X_LOCATION") String location) {

        CurrentUser currentUser = CurrentUser.getInstance(userId, username, authority, location);

		return this.discoveryService.fetcInventoris(currentUser);
	}
}
