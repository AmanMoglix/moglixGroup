package com.hackathon.catalog.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.hackathon.catalog.domain.Catalog;
import com.hackathon.catalog.model.CurrentUser;
import com.hackathon.catalog.repository.CatalogRepositroy;
import com.hackathon.catalog.service.CatalogService;
@Service
@Transactional
public class CatalogServiceImpl implements CatalogService {
    private static Logger logger = LoggerFactory.getLogger(CatalogServiceImpl.class);
@Autowired
private CatalogRepositroy catalogRepositroy;
	@Override
	public Catalog saveOrUpdate(Catalog catalog, CurrentUser currentUser) {
//		if(this.getById(catalog.getId())!=null) {
//			logger.info("Entity is Going to update user by '{}'", new Gson().toJson(currentUser));
//		return 	this.catalogRepositroy.save(catalog);
//		}else
			logger.info(" Entity Going to persist user by '{}'", new Gson().toJson(currentUser));
		return this.catalogRepositroy.save(catalog);
	}

	@Override
	public Catalog getById(Long catalogId) {
		logger.info("Getting product by catalogIs" + catalogId);

		Catalog catalog=this.catalogRepositroy.getById(catalogId);
		logger.info("Getting product by catalogIs" + catalog);
		if(catalog!=null) {
			return catalog;
		}
		return null;
	}

	@Override
	public List<Catalog> list() {
		return this.catalogRepositroy.findAll();
	}

	@Override
	public void delete(Long catalogId) {
		// TODO Auto-generated method stub

	}

}
