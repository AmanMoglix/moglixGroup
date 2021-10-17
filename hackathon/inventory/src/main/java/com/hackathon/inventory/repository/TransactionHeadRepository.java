package com.hackathon.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hackathon.inventory.domain.TransactionHead;
@Repository
public interface TransactionHeadRepository extends JpaRepository<TransactionHead,Long>{

}
