package com.hackathon.inventory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hackathon.inventory.model.CurrentUser;
import com.hackathon.inventory.model.dto.StockDTO;
import com.hackathon.inventory.service.StockService;
@RestController
@RequestMapping(value="/inventory/stocks")
public class StockController {
	@Autowired
	private StockService stockService;
	
	@RequestMapping(value = "", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public List<StockDTO> getById(@RequestHeader("X_AUTHORITY") String authority, @RequestHeader("X_USER_ID") String userId,
			@RequestHeader("X_USERNAME") String username, @RequestHeader("X_LOCATION") String location) {

        CurrentUser currentUser = CurrentUser.getInstance(userId, username, authority, location);

		return this.stockService.getStocksByCurrentUser(currentUser);
	}
}
