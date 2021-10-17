package com.hackathon.inventory.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.hackathon.inventory.domain.Stock;
import com.hackathon.inventory.model.CurrentUser;
import com.hackathon.inventory.model.dto.StockDTO;
import com.hackathon.inventory.model.dto.discovery.CatalogDiscoveryDTO;
import com.hackathon.inventory.repository.StockRepository;
import com.hackathon.inventory.service.DiscoveryService;
import com.hackathon.inventory.service.StockService;
import com.hackathon.inventory.utils.ObjectBinder;
import com.hackathon.inventory.utils.exception.NotFoundException;

@Service
public class StockServiceImpl implements StockService {
	private static final Logger logger = LoggerFactory.getLogger(StockServiceImpl.class);
	@Autowired
	private StockRepository stockRepository;
	@Autowired
	private ObjectBinder objectBinder;
	@Autowired
	private DiscoveryService discoverService;

	@Override
	public StockDTO saveOrUpdate(Stock stock, CurrentUser currentUser) {
		if (stock.getId() == null) {
			logger.info("Persisting Stock Entity by user: {}", new Gson().toJson(stock));
			return this.objectBinder.bindStock(this.stockRepository.save(stock));

		} else if (stock.getId() != null) {
			logger.info("Updating Stock Entity by user: {}", new Gson().toJson(currentUser));
			return this.objectBinder.bindStock(this.stockRepository.save(stock));
		}
		return null;
	}

	@Override
	public StockDTO getById(Long stockId, CurrentUser currentUser) {
		logger.info("Getting stock entity by id '{}'", stockId);
		Stock stock = this.stockRepository.getById(stockId);
		if (stock == null)
			throw new NotFoundException("Stock does not exits with this stockId" + stockId);

		StockDTO stockDTO=this.objectBinder.bindStock(stock);
		stockDTO.setCatalog(getCatalogByProductId(stock.getProductId(), currentUser));
		return stockDTO;
	}

	@Override
	public List<StockDTO> list(CurrentUser currentUser) {
		List<StockDTO>  stockDTOs=this.objectBinder.bindStocks(this.stockRepository.findAll());
		stockDTOs.forEach(it->{
			if(it!=null) {
				it.setCatalog(getCatalogByProductId(it.getProductId(), currentUser));
			}
		});
		return stockDTOs;
	}

	@Override
	public void delete(Long stockId, CurrentUser currentUser) {
		// TODO Auto-generated method stub

	}

	private CatalogDiscoveryDTO getCatalogByProductId(Long productId, CurrentUser currentUser) {
		logger.info("Getting product by productId :'{}'", productId);

		CatalogDiscoveryDTO catalogDiscoveryDTO = this.discoverService.fetchByProductId(productId, currentUser);
		if (catalogDiscoveryDTO == null) {
			throw new NotFoundException("Product not found by " + productId);
		}
		
		return catalogDiscoveryDTO;
	}

	@Override
	public Stock findByProductIdAndInventoryId(Long productId, Long inventoryId,CurrentUser currentUser) {
		logger.info("Getting Stocks by products and inventoryId ,'{}' , '{}'" ,  productId ,inventoryId);
		Stock stock =this.stockRepository.findByProductIdAndInventoryId(productId, Long.valueOf(currentUser.getUserId()));
	//	if(stock ==null)
	//		throw new NotFoundException("Stock does not exits with this productId and inventoryId" + productId +inventoryId);

		return stock;
	}

	@Override
	public List<StockDTO> getStocksByCurrentUser(CurrentUser currentUser) {
		logger.info("Getting stocks by currentUser :'{}'" ,currentUser);
		List<StockDTO> stockDTOs=new ArrayList<StockDTO>();
		List<Stock> stList= this.stockRepository.getStockListByUserId(Long.valueOf(currentUser.getUserId()));
		for (Stock stock : stList) {
			StockDTO stockDTO=this.objectBinder.bindStock(stock);
			stockDTO.setCatalog(this.discoverService.fetchByProductId(stock.getProductId(), currentUser));
			stockDTOs.add(stockDTO);
		}
		return stockDTOs;
	}
}
