package com.hackathon.supplier.sevice.impl;

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
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.hackathon.supplier.config.ApplicationConfig;
import com.hackathon.supplier.model.CurrentUser;
import com.hackathon.supplier.model.dto.discovery.CatalogDiscoveryDTO;
import com.hackathon.supplier.model.dto.discovery.InventoryDiscoveryDTO;
import com.hackathon.supplier.model.dto.discovery.OrderDiscoveryDTO;
import com.hackathon.supplier.sevice.DiscoveryService;
import com.hackathon.supplier.utils.exception.DiscoveryServiceException;

@Service
public class DiscoverServiceImpl implements DiscoveryService {
	private static final Logger logger = LoggerFactory.getLogger(DiscoverServiceImpl.class);
	@Autowired
	private ApplicationConfig applicationConfig;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private LoadBalancerClient loadBalancerClient;

//get service instance via ServiceInatance //-->loadBalancer exted ServiceInstanceChooser interface
	private ServiceInstance getServiceInstance(String serviceId) {
		ServiceInstance serviceInstance = this.loadBalancerClient.choose(serviceId);
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
			headers.set("X_LOCATION", "L-DefaultLocation");
		}
		return headers;
	}

	@Override
	public List<CatalogDiscoveryDTO> fetchCatalogs(CurrentUser currentUser) {
		logger.info("Going to fetch catalogs list by user :{}", currentUser);
		try {
			String PATH = "/catalog/";
			String url = getServiceInstance(applicationConfig.getCatalogServiceId()).getUri() + PATH;
			HttpHeaders headers = getHeaders(false, currentUser);
			logger.info("Requesting to fetch  catalogs by currentUSer");
			logger.info("Request URL for Catalog by user :{}", url);
			logger.info("Request Headers for Catalogs by currentUser:{}", new Gson().toJson(headers));
			ResponseEntity<List<CatalogDiscoveryDTO>> responseEntity = this.restTemplate.exchange(url, HttpMethod.GET,
					new HttpEntity<>(headers), new ParameterizedTypeReference<List<CatalogDiscoveryDTO>>() {
					});
			int httpStatus = responseEntity.getStatusCodeValue();
			logger.info("Fetch Catalogs by currentUser request is processed with service HTTP code {}", httpStatus);
			if (httpStatus == HttpStatus.OK.value()) {
				logger.debug("Data fetched from catalog service :{}", new Gson().toJson(responseEntity.getBody()));
				List<CatalogDiscoveryDTO> response = responseEntity.getBody();
				return response == null ? new ArrayList<>() : response;
			}
		} catch (HttpClientErrorException.BadRequest | HttpClientErrorException.Conflict
				| HttpClientErrorException.NotFound exception) {
			exception.printStackTrace();
			// logger.error("catalog Service error during request of fetching users by
			// location.");
			throw new com.hackathon.supplier.utils.exception.DiscoveryRequestException(
					"Catalog Service error during request of fetching users by location.");
		}
		return new ArrayList<>();
	}

	@Override
	public List<OrderDiscoveryDTO> fetchAssignedOrders(CurrentUser currentUser) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<InventoryDiscoveryDTO> fetcInventoris(CurrentUser currentUser) {
		logger.info("Going to fetch stocks by supplier: '{}'", new Gson().toJson(currentUser));
		try {
			String PATH = "/inventory/stocks/";
			String url = getServiceInstance(applicationConfig.getInventoryServiceId()).getUri() + PATH;
			HttpHeaders headers = getHeaders(false, currentUser);

			logger.info("Requesting to fetch  stocks by currentUser");
			logger.info("Request URL for stocks by user :{}", url);
			logger.info("Request Headers for stocks by currentUser:{}", new Gson().toJson(headers));

			ResponseEntity<List<InventoryDiscoveryDTO>> responseEntity = this.restTemplate.exchange(url, HttpMethod.GET,
					new HttpEntity<>(headers), new ParameterizedTypeReference<List<InventoryDiscoveryDTO>>() {
					});

			int httpStatus = responseEntity.getStatusCodeValue();
			logger.info("Fetch stocks by currentUser request is processed with service HTTP code {}", httpStatus);
			if (httpStatus == HttpStatus.OK.value()) {
				logger.debug("Data fetched from stocks service :{}", new Gson().toJson(responseEntity.getBody()));
				List<InventoryDiscoveryDTO> response = responseEntity.getBody();
				return response == null ? new ArrayList<>() : response;
			}

		} catch (HttpClientErrorException.BadRequest | HttpClientErrorException.Conflict
				| HttpClientErrorException.NotFound exception) {
			exception.printStackTrace();
			// logger.error("catalog Service error during request of fetching users by
			// location.");
			throw new com.hackathon.supplier.utils.exception.DiscoveryRequestException(
					"Catalog Service error during request of fetching users by location.");
		}
		return new ArrayList<>();

	}

	@Override
	public CatalogDiscoveryDTO fectchByProductId(Long productId, CurrentUser currentUser) {
		logger.info("Going to fetch product by productId '{}'", productId);
		
		try {
			String PATH = "/catalog/" + productId;
			String url = getServiceInstance(applicationConfig.getCatalogServiceId()).getUri() + PATH;
			HttpHeaders headers = getHeaders(false, currentUser);
			logger.info("Requesting to fetch  product by currentUser");
			logger.info("Request URL for product by productId :{}", url);
			logger.info("Request Headers for product by currentUser:{}", new Gson().toJson(headers));
			
			ResponseEntity<CatalogDiscoveryDTO> responseEntity = this.restTemplate.exchange(url, HttpMethod.GET,
					new HttpEntity<>(headers), new ParameterizedTypeReference<CatalogDiscoveryDTO>() {
					});
			
			int httpStatus = responseEntity.getStatusCodeValue();
			if (httpStatus == HttpStatus.OK.value()) {
				CatalogDiscoveryDTO response = responseEntity.getBody();
				return response == null ? new CatalogDiscoveryDTO() : response;
			}
			
		} catch (HttpClientErrorException.BadRequest | HttpClientErrorException.Conflict
				| HttpClientErrorException.NotFound exception) {
			exception.printStackTrace();

			throw new com.hackathon.supplier.utils.exception.DiscoveryRequestException(
					"Catalog Service error during request of fetching product by productId.");
		}
		return new CatalogDiscoveryDTO();
	}

}
