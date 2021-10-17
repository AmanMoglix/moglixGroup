package com.hackathon.catalog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hackathon.catalog.domain.Catalog;
import com.hackathon.catalog.model.CurrentUser;
import com.hackathon.catalog.model.co.TransactionHeadCO;
import com.hackathon.catalog.service.DiscoveryService;

@RestController
@RequestMapping(value = "/catalog/inventory/")
public class InventoryController {
	@Autowired
	private DiscoveryService discoveryService;

	@RequestMapping(value = "", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Void> save(@RequestBody TransactionHeadCO transactionHeadCO,
			@RequestHeader("X_AUTHORITY") String authority, @RequestHeader("X_USER_ID") String userId,
			@RequestHeader("X_USERNAME") String username, @RequestHeader("X_LOCATION") String location) {
        CurrentUser currentUser = CurrentUser.getInstance(userId, username, authority, location);
		try {
			this.discoveryService.saveOrUpdate(transactionHeadCO, currentUser);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();// TODO: handle exception
		}
	}
}
