package com.hackathon.inventory.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hackathon.inventory.domain.Inventory;
import com.hackathon.inventory.model.CurrentUser;
import com.hackathon.inventory.model.dto.InventoryDTO;
import com.hackathon.inventory.service.InventoryService;
import com.sun.istack.NotNull;



@RestController
@RequestMapping(value = "/inventory")
public class InventoryController {
	private static final Logger logger = LoggerFactory.getLogger(InventoryController.class);
	@Autowired
	private InventoryService inventoryService;
	
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public Inventory saveOrUpdate(@RequestBody Inventory inventory, @RequestHeader("X_AUTHORITY") String authority,
			@RequestHeader("X_USER_ID") String userId, @RequestHeader("X_USERNAME") String username,
			@RequestHeader("X_LOCATION") String location) {
		logger.info("CataLog perssitent ");
        CurrentUser currentUser = CurrentUser.getInstance(userId, username, authority, location);

		return inventoryService.saveOrUpdate(inventory, currentUser);
	}
	@RequestMapping(value = "", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public List<InventoryDTO> list(@RequestHeader("X_AUTHORITY") String authority, @RequestHeader("X_USER_ID") String userId,
			@RequestHeader("X_USERNAME") String username, @RequestHeader("X_LOCATION") String location) {

        CurrentUser currentUser = CurrentUser.getInstance(userId, username, authority, location);

		return this.inventoryService.list(currentUser);
	}
	@RequestMapping(value = "/{inventoryId}", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public InventoryDTO getById(@PathVariable("inventoryId") Long inventoryId,@RequestHeader("X_AUTHORITY") String authority, @RequestHeader("X_USER_ID") String userId,
			@RequestHeader("X_USERNAME") String username, @RequestHeader("X_LOCATION") String location) {

        CurrentUser currentUser = CurrentUser.getInstance(userId, username, authority, location);

		return this.inventoryService.getById(inventoryId, currentUser);
	}
	    @RequestMapping(value = "/{inventoryId}", method = RequestMethod.DELETE, consumes = "application/json", produces = "application/json")
	    @ResponseStatus(HttpStatus.OK)
	    public ResponseEntity<Void> deleteCategory(@PathVariable("inventoryId") Long inventoryId, @RequestHeader("X_AUTHORITY") String authority,
	                                    @RequestHeader("X_USER_ID") String userId, @RequestHeader("X_USERNAME") @NotNull  String username,
	                                    @RequestHeader("X_LOCATION") String location) {

	        CurrentUser currentUser = CurrentUser.getInstance(userId, username, authority, location);

	        try {
				this.inventoryService.delete(inventoryId, currentUser);
				return ResponseEntity.ok().build();
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
	        
	    }
	    
	    
}
