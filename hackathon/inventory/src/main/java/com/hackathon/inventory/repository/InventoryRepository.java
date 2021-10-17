package com.hackathon.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hackathon.inventory.domain.Inventory;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

}
