package com.hackathon.inventory.controller;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hackathon.inventory.domain.TransactionHead;
import com.hackathon.inventory.model.CurrentUser;
import com.hackathon.inventory.service.TransactionHeadService;

@RestController
@RequestMapping(value = "/inventory/txn")
public class InventoryTransactionController {
	private static final Logger logger = LoggerFactory.getLogger(InventoryTransactionController.class);
	@Autowired
	private TransactionHeadService transactionHeadService;

	@RequestMapping(value = "", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public TransactionHead save(@RequestBody TransactionHead transactionHead,
			@RequestHeader("X_AUTHORITY") String authority, @RequestHeader("X_USER_ID") String userId,
			@RequestHeader("X_USERNAME") String username, @RequestHeader("X_LOCATION") String location) {

		CurrentUser currentUser = CurrentUser.getInstance(userId, username, authority, location);

		return this.transactionHeadService.saveOrUpdate(transactionHead, currentUser);
	}

	@RequestMapping(value = "/txnHeadId", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public TransactionHead getById(@PathVariable("txnHeadId") Long txnHeadId,
			@RequestHeader("X_AUTHORITY") String authority, @RequestHeader("X_USER_ID") String userId,
			@RequestHeader("X_USERNAME") String username, @RequestHeader("X_LOCATION") String location) {

		CurrentUser currentUser = CurrentUser.getInstance(userId, username, authority, location);

		return this.transactionHeadService.getById(txnHeadId, currentUser);
	}

	@RequestMapping(value = "", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public List<TransactionHead> list(@RequestHeader("X_AUTHORITY") String authority,
			@RequestHeader("X_USER_ID") String userId, @RequestHeader("X_USERNAME") String username,
			@RequestHeader("X_LOCATION") String location) {

		CurrentUser currentUser = CurrentUser.getInstance(userId, username, authority, location);

		return this.transactionHeadService.list(currentUser);
	}

	@RequestMapping(value = "/{txnHeadId}", method = RequestMethod.DELETE, consumes = "application/json", produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public void deleteCategory(@PathVariable("txnHeadId") Long txnHeadId,
			@RequestHeader("X_AUTHORITY") String authority, @RequestHeader("X_USER_ID") String userId,
			@RequestHeader("X_USERNAME") String username, @RequestHeader("X_LOCATION") String location) {

		CurrentUser currentUser = CurrentUser.getInstance(userId, username, authority, location);

		transactionHeadService.delete(txnHeadId, currentUser);
	}
}
