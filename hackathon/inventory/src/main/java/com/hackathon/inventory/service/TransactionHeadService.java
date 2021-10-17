package com.hackathon.inventory.service;

import java.util.List;

import com.hackathon.inventory.domain.TransactionHead;
import com.hackathon.inventory.model.CurrentUser;

public interface TransactionHeadService {
	public TransactionHead saveOrUpdate(TransactionHead transactionHead, CurrentUser currentUser);

	public TransactionHead getById(Long txnHeadId, CurrentUser currentUser);

	public List<TransactionHead> list(CurrentUser currentUser);

	public void delete(Long txnHeadId, CurrentUser currentUser);
}
