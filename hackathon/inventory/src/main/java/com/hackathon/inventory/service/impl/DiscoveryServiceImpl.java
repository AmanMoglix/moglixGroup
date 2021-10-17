package com.hackathon.inventory.service.impl;

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
import com.hackathon.inventory.config.ApplicationConfig;
import com.hackathon.inventory.model.CurrentUser;
import com.hackathon.inventory.model.dto.discovery.CatalogDiscoveryDTO;
import com.hackathon.inventory.service.DiscoveryService;
import com.hackathon.inventory.utils.exception.DiscoveryRequestException;
import com.hackathon.inventory.utils.exception.DiscoveryServiceException;

@Service
public class DiscoveryServiceImpl implements DiscoveryService {
	private static final Logger logger = LoggerFactory.getLogger(DiscoveryService.class);

	@Autowired
	private ApplicationConfig applicationConfig;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private LoadBalancerClient loadBalancerClient;

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
			headers.set("X_LOCATION", "L-DefualtLocation");
		}
		return headers;
	}

	@Override
	public CatalogDiscoveryDTO fetchByProductId(Long productId, CurrentUser currentUser) {
		logger.info("Going to fetch product by productId :{}", productId);
		try {
			String PATH = "/catalog/"+productId;
			String url = getServiceInstance(this.applicationConfig.getCatalogServiceId()).getUri() + PATH;
			HttpHeaders headers = getHeaders(false, currentUser);

			logger.info("Requesting to fetch product by productId");
			logger.info("Request URL for Product by productId :{}", url);
			logger.info("Request Headers for Product by productId:{}", new Gson().toJson(headers));

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

			throw new DiscoveryRequestException(
					"Catalog Service error during request of fetching product by productId.");
		}

		return new CatalogDiscoveryDTO();
	}

}
