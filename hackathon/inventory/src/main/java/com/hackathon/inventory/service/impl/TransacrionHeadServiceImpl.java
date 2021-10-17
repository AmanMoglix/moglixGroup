package com.hackathon.inventory.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hackathon.inventory.domain.Stock;
import com.hackathon.inventory.domain.TransactionDetails;
import com.hackathon.inventory.domain.TransactionHead;
import com.hackathon.inventory.model.CurrentUser;
import com.hackathon.inventory.repository.TransactionHeadRepository;
import com.hackathon.inventory.service.StockService;
import com.hackathon.inventory.service.TransactionHeadService;
import com.hackathon.inventory.utils.ObjectBinder;

@Service
public class TransacrionHeadServiceImpl implements TransactionHeadService {
	private static final Logger logger = LoggerFactory.getLogger(TransacrionHeadServiceImpl.class);
	@Autowired
	private TransactionHeadRepository transactionHeadRepository;
	@Autowired
	private StockService stockService;
	@Autowired
	private ObjectBinder objectBinder;

	@Override
	public TransactionHead saveOrUpdate(TransactionHead transactionHead, CurrentUser currentUser) {
		logger.info("Going to Persisting TxnHead ,{}", transactionHead);
		Long inventoryId = transactionHead.getInventoryId();
		if (inventoryId != null) {
			List<TransactionDetails> txnDetails = transactionHead.getTxnDetailsCO();

			// *****inward txn ******/ quantity+existing quantity
			if (transactionHead.getType().equalsIgnoreCase("inward")) {
				for (TransactionDetails txn : txnDetails) {

					if (txn.getProductId() != null && txn.getProductId() > 0 && txn.getQuantity() > 0) {
						Stock stock = this.stockService.findByProductIdAndInventoryId(txn.getProductId(),inventoryId,currentUser);
						if (stock != null) {
							// adding the quantity
							Integer currentQuantity = txn.getQuantity() + stock.getQuantity();
							stock.setQuantity(currentQuantity);
							Double averageAmount = ((stock.getProductPrice() * stock.getQuantity()
									+ txn.getProductPrice() * txn.getQuantity()) / stock.getQuantity()
									+ txn.getQuantity());
							stock.setProductPrice(averageAmount);
							this.stockService.saveOrUpdate(stock, currentUser);
						} else {
							Stock stk = new Stock();
							/*stk.setInventoryId(inventoryId)*/;
							stk.setUserId(transactionHead.getSupplierId());
							stk.setProductId(txn.getProductId());
							stk.setProductPrice(txn.getProductPrice());
							stk.setQuantity(txn.getQuantity());
							this.stockService.saveOrUpdate(stk, currentUser);
						}

					}
				}
			} else if (transactionHead.getType().equalsIgnoreCase("outward")) {
				///need to substract quantity
				for (TransactionDetails txn : txnDetails) {
					if (txn.getProductId() != null && txn.getProductId() > 0) {
						Stock stock = this.stockService.findByProductIdAndInventoryId(txn.getProductId(), inventoryId,currentUser);
						if (stock != null && txn.getQuantity() > 0 && stock.getQuantity() > txn.getQuantity()) {
							Integer currentQuantity = stock.getQuantity() - txn.getQuantity();
							stock.setQuantity(currentQuantity);
							this.stockService.saveOrUpdate(stock, currentUser);
						}
					}
				}
			}
		}
		return this.transactionHeadRepository.save(transactionHead);
	}

	@Override
	public TransactionHead getById(Long txnHeadId, CurrentUser currentUser) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TransactionHead> list(CurrentUser currentUser) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long txnHeadId, CurrentUser currentUser) {
		// TODO Auto-generated method stub

	}

}
