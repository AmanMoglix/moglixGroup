package com.hackathon.inventory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hackathon.inventory.domain.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

	@Query(value = "Select s from com.hackathon.inventory.domain.Stock s where s.productId = :productId and s.userId = :userId")
	public Stock findByProductIdAndInventoryId(Long productId, Long userId);
	
	@Query(value = "Select s from com.hackathon.inventory.domain.Stock s where s.userId = :userId ")
	public List<Stock> getStockListByUserId(Long userId);
}
