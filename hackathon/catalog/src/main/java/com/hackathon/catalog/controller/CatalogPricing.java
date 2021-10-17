package com.hackathon.catalog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hackathon.catalog.domain.Catalog;
import com.hackathon.catalog.model.CurrentUser;
import com.hackathon.catalog.service.CatalogService;

@RestController
@RequestMapping(value="/catalog/addPricing")
public class CatalogPricing {
	@Autowired
	private CatalogService catalogService;
	public Catalog addPricing(@RequestBody Catalog catalog, @RequestHeader("X_AUTHORITY") String authority,
			@RequestHeader("X_USER_ID") String userId, @RequestHeader("X_USERNAME") String username,
			@RequestHeader("X_LOCATION") String location) {
        CurrentUser currentUser = CurrentUser.getInstance(userId, username, authority, location);

		return catalogService.saveOrUpdate(catalog, currentUser);
	}
}
