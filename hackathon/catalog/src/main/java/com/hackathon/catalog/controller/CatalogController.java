package com.hackathon.catalog.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hackathon.catalog.domain.Catalog;
import com.hackathon.catalog.model.CurrentUser;
import com.hackathon.catalog.service.CatalogService;

@RestController
@RequestMapping(value = "/catalog")
public class CatalogController {
	@Autowired
	private CatalogService catalogService;

	private static Logger logger = LoggerFactory.getLogger(CatalogController.class);

	@RequestMapping(value = "", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
//	public Catalog save(@RequestBody Catalog catalog) {
	public Catalog save(@RequestBody Catalog catalog, @RequestHeader("X_AUTHORITY") String authority,
			@RequestHeader("X_USER_ID") String userId, @RequestHeader("X_USERNAME") String username,
			@RequestHeader("X_LOCATION") String location) {
		logger.info("CataLog perssitent ");
        CurrentUser currentUser = CurrentUser.getInstance(userId, username, authority, location);

		return catalogService.saveOrUpdate(catalog, currentUser);
	}

//	    @ApiOperation(value = "Update category (Menu [Inventory-> Category-> Update category])")
//	    @RequestMapping(value = "/{categoryId}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
//	    public Catalogupdate(@CategoryDTO  CategoryDTO ApiParam(name = " ", value = " ") @RequestBody CategoryCO categoryCO, @PathVariable("categoryId") String categoryId,
//	                              BindingResult bindingResult, @RequestHeader("X_AUTHORITY") String authority, @RequestHeader("X_USER_ID") String userId,
//	                              @RequestHeader("X_USERNAME") @NotNull @NotBlank String username, @RequestHeader("X_LOCATION") String location) {
//
//	        CurrentUser currentUser = CurrentUser.getInstance(userId, username, authority, location);
//
//	        if (bindingResult.hasErrors())
//	            throw new BadRequestException("Bad Update Category Request. Params Missing");
//
//	        return categoryService.update(categoryCO, categoryId, currentUser);
//	    }

	@RequestMapping(value = "", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public List<Catalog> list(@RequestParam(name = "search", required = false) String category,
			@RequestHeader("X_AUTHORITY") String authority, @RequestHeader("X_USER_ID") String userId,
			@RequestHeader("X_USERNAME") String username, @RequestHeader("X_LOCATION") String location) {

        CurrentUser currentUser = CurrentUser.getInstance(userId, username, authority, location);

		return this.catalogService.list();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public Catalog getById(@PathVariable("id") Long id, @RequestHeader("X_AUTHORITY") String authority,
			@RequestHeader("X_USER_ID") String userId, @RequestHeader("X_USERNAME") String username,
			@RequestHeader("X_LOCATION") String location) {

        CurrentUser currentUser = CurrentUser.getInstance(userId, username, authority, location);
		return this.catalogService.getById(id);
	}

}
