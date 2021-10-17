package com.hackathon.catalog.service;

import java.util.List;

import com.hackathon.catalog.domain.Catalog;
import com.hackathon.catalog.model.CurrentUser;

public interface CatalogService {
	public Catalog saveOrUpdate(Catalog catalog, CurrentUser currentUser);

	public Catalog getById(Long catalogId);

	public List<Catalog> list();

	public void delete(Long catalogId);
}
