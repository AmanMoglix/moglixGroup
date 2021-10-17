package com.hackathon.catalog.service;



import java.util.List;

import com.hackathon.catalog.model.CurrentUser;
import com.hackathon.catalog.model.co.TransactionHeadCO;
import com.hackathon.catalog.model.dto.discovery.UserDiscoveryDTO;

public interface DiscoveryService {

    public void syncLocation();

    public List<UserDiscoveryDTO> fetchUserByLocation(String locationId, CurrentUser currentUser);

	public void saveOrUpdate(TransactionHeadCO transactionHeadCO, CurrentUser currentUser);
}
