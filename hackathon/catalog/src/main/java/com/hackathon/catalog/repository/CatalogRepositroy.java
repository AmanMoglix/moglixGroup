package com.hackathon.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hackathon.catalog.domain.Catalog;
@Repository
public interface CatalogRepositroy extends JpaRepository<Catalog,Long> {

}
