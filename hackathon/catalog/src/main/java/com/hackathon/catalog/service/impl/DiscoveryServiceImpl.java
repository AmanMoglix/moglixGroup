package com.hackathon.catalog.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.hackathon.catalog.config.ApplicationConfig;
import com.hackathon.catalog.model.CurrentUser;
import com.hackathon.catalog.model.co.TransactionHeadCO;
import com.hackathon.catalog.model.dto.discovery.UserDiscoveryDTO;
import com.hackathon.catalog.service.DiscoveryService;
import com.hackathon.catalog.utils.exception.DiscoveryRequestException;
import com.hackathon.catalog.utils.exception.DiscoveryServiceException;

@Service
public class DiscoveryServiceImpl implements DiscoveryService {
	private static final Logger logger = LoggerFactory.getLogger(DiscoveryServiceImpl.class);
	@Autowired
	private ApplicationConfig config;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private LoadBalancerClient loadBalancerClient;

	private ServiceInstance getServiceInstance(String serviceId) {
		ServiceInstance serviceInstance = loadBalancerClient.choose(serviceId);

		if (serviceInstance == null)
			throw new DiscoveryServiceException("Service Unavailable '{" + serviceId
					+ "}'. Due to unavailability we can not serve this functionality.");

		return serviceInstance;
	}

	private HttpHeaders getHeaders(boolean isOnlyContentTypeRequired, CurrentUser currentUser) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		if (!isOnlyContentTypeRequired) {
			headers.set("X_USER_ID", currentUser.getUserId());
			headers.set("X_USERNAME", currentUser.getUsername());
			headers.set("X_AUTHORITY", currentUser.getAuthority().toString());
			headers.set("X_LOCATION","L-DefaultLocation");
		}
		return headers;
	}

	@Override
	public void syncLocation() {
		logger.info("Async call for location DB synchronization.");

		try {
			String PATH = "/schedule/sync/location";
			String url = getServiceInstance(/* config.getMigrationServiceId() */null).getUri() + PATH;
			HttpHeaders headers = getHeaders(true, null);

			logger.info("Requesting to sync locations between SSO and Inventory");
			logger.info("Sync Request URL :{}", url);
			logger.info("Sync Request Headers :{}", headers);

			ResponseEntity<Void> responseEntity = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers),
					Void.class);

			int httpStatus = responseEntity.getStatusCodeValue();
			logger.info("Sync location request processed with service HTTP code {}", httpStatus);

		} catch (HttpClientErrorException.BadRequest | HttpClientErrorException.Conflict
				| HttpClientErrorException.NotFound exception) {
			exception.printStackTrace();
			logger.error("Migration Service Error during location data synchronization");
			throw new DiscoveryRequestException("Migration Service Error during location data synchronization");
		} catch (DiscoveryServiceException exception) {
			exception.printStackTrace();
		}

	}

	@Override
	public List<UserDiscoveryDTO> fetchUserByLocation(String locationId, CurrentUser currentUser) {
		logger.info("Going to fetch user by location :{}", locationId);

		try {
			String PATH = "/location/user/" + locationId;
			String url = getServiceInstance(config.getAuthServiceId()).getUri() + PATH;
			HttpHeaders headers = getHeaders(false, currentUser);

			logger.info("Requesting to fetch users by location");
			logger.info("Request URL for Users by location :{}", url);
			logger.info("Request Headers for Users by location:{}", new Gson().toJson(headers));

			ResponseEntity<List<UserDiscoveryDTO>> responseEntity = restTemplate.exchange(url, HttpMethod.GET,
					new HttpEntity<>(headers), new ParameterizedTypeReference<List<UserDiscoveryDTO>>() {
					});

			int httpStatus = responseEntity.getStatusCodeValue();
			logger.info("Fetch Users by location request is processed with service HTTP code {}", httpStatus);

			if (httpStatus == HttpStatus.OK.value()) {
				logger.debug("Data fetched from sso service :{}", new Gson().toJson(responseEntity.getBody()));
				List<UserDiscoveryDTO> response = responseEntity.getBody();
				return response == null ? new ArrayList<>() : response;
			}
		} catch (HttpClientErrorException.BadRequest | HttpClientErrorException.Conflict
				| HttpClientErrorException.NotFound exception) {
			exception.printStackTrace();
			logger.error("SSO Service error during request of fetching users by location.");
			throw new DiscoveryRequestException("SSO Service error during request of fetching users by location.");
		}
		return new ArrayList<>();
	}

	@Override
	public void saveOrUpdate(TransactionHeadCO transactionHeadCO, CurrentUser currentUser) {
		logger.info("Going to fetch update inventory by supplier :{}", transactionHeadCO);
		try {
			String PATH = "/inventory/txn";
			String url = getServiceInstance(this.config.getInventoryServiceId()).getUri() + PATH;
			HttpHeaders headers = getHeaders(false, currentUser);

			logger.info("Requesting to update inventory by supplier");
			logger.info("Request URL for Inventory by supplier :{}", url);
			logger.info("Request Headers for Inventory by supplier :{}", new Gson().toJson(headers));

			HttpEntity<TransactionHeadCO> httpEntity = new HttpEntity<>(transactionHeadCO, headers);
			ResponseEntity<Void> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, Void.class);
			int httpStatus = responseEntity.getStatusCodeValue();
			logger.info("Inventory stock updation request processed with service HTTP code {}", httpStatus);
		} catch (HttpClientErrorException.BadRequest | HttpClientErrorException.Conflict
				| HttpClientErrorException.NotFound exception) {
			exception.printStackTrace();
			throw new DiscoveryRequestException("Inventory Service error during submitting the TransactionHead request");
		} catch (DiscoveryServiceException exception) {
			exception.printStackTrace();
		}
	}

}
