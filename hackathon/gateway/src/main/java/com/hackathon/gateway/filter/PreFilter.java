package com.hackathon.gateway.filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.hackathon.gateway.config.AccessAuthorityConfig;
import com.hackathon.gateway.model.Token;
import com.hackathon.gateway.service.AuthenticationService;
import com.hackathon.gateway.utils.enumeration.ApplicationRole;
import com.hackathon.gateway.utils.exception.AlreadyLoggedInException;
import com.hackathon.gateway.utils.exception.AuthenticationException;
import com.hackathon.gateway.utils.exception.HeaderMissingException;
import com.hackathon.gateway.utils.exception.InvalidTokenException;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

public class PreFilter extends ZuulFilter {

	private static final String LOGIN_URL = "/api/login";
//	private static final String OMS_URL = "/oms";
//	private static final String CATALOG_URL = "/catalog";

	private static final String X_USER_ID = "X_USER_ID";

	private static final String X_USERNAME = "X_USERNAME";

	private static final String X_ROLE = "X_AUTHORITY";

	private static final String X_LOCATION = "X_LOCATION";
	
	private static final String AUTH_HEADER = "Authorization";
	
	public static final String TOKEN_PREFIX = "Bearer ";


	private static final String[] HEADERS = { "Authorization", "Content-Type" };

	private static final String[] NO_AUTHENTICATION = { LOGIN_URL,"/catalog/master/location" }; // collection can not
																									// have duplicate
																									// URL

	private static Logger log = LoggerFactory.getLogger(PreFilter.class);

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private AccessAuthorityConfig accessAuthorityConfig;

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		log.info("Request Filtration Process");
		RequestContext requestContext = RequestContext.getCurrentContext();
		HttpServletRequest request = requestContext.getRequest();

		log.info("Method : {} Request URI : {}", request.getMethod(), request.getRequestURI());

		boolean isAuthNotRequired = isAuthNotRequired(request);
		
		boolean isHeaderOk = checkRequestHeaders(request, isAuthNotRequired);
		System.out.println("***************"+isHeaderOk);

		if (!isHeaderOk)
			throw new HeaderMissingException("Required headers are missing..!");

		if (!isAuthNotRequired)
			authenticationFilter(request, requestContext);
		return null;
	}

	private boolean isAuthNotRequired(HttpServletRequest request) {
		List<Boolean> status = new ArrayList<>();

		for (String url : NO_AUTHENTICATION) {
			if (request.getRequestURI().contains(url))
				status.add(true);
		}
		return (status.contains(true) && status.size() == 1);
	}

	private void authenticationFilter(HttpServletRequest request, RequestContext requestContext) {
		Token userToken = tokenFilter(request, requestContext);
		roleAuthFilter(request, userToken);
	}

	private Token tokenFilter(HttpServletRequest request, RequestContext requestContext) {
		log.info("Token validation process...");
		String[] token = checkIfTokenExists(request);
		String header = request.getHeader(AUTH_HEADER);
		//String[] encoded = header.split("\\.");
		String toke=header.replace(TOKEN_PREFIX, "");
		Token userToken = authenticationService.authenticate(toke);
		String cm=token[4];
	    String name=cm.replace(",\"firstname\"","");
	    System.out.println(name.trim().replaceAll("^\"|\"$", ""));
		
		if (userToken == null)
			throw new InvalidTokenException("Access denied. Token can not be empty.");
		
		if (!userToken.getUsername().contentEquals(name.trim().replaceAll("^\"|\"$", "")))
			throw new AuthenticationException("Invalid username or JWT token");

		requestContext.addZuulRequestHeader(X_USER_ID, String.valueOf(userToken.getUserId()));
		requestContext.addZuulRequestHeader(X_USERNAME, userToken.getUsername());
		requestContext.addZuulRequestHeader(X_ROLE, userToken.getRole());
		requestContext.addZuulRequestHeader(X_LOCATION, "L-DefaultLocation");

		return userToken;
	}

	private void roleAuthFilter(HttpServletRequest request, Token userToken) {
		log.info("Validating api access for authentication.");
		log.info("Role: {} -> Method: {} -> Url: {}", ApplicationRole.from(userToken.getRole()),
				RequestMethod.valueOf(request.getMethod()), request.getRequestURI());

		/*
		 * boolean isAuthorised =
		 * accessAuthorityConfig.isAuthorisedAccess(ApplicationRole.from(userToken.
		 * getRole()), RequestMethod.valueOf(request.getMethod()),
		 * request.getRequestURI());
		 * 
		 * if (!isAuthorised) throw new
		 * AuthenticationException("Unauthorised access to fetch this resource by this user's credentials"
		 * );
		 */
	}

	private String[] checkIfTokenExists(HttpServletRequest request) {
		log.info("checking token... Is it there or not");
		String header = request.getHeader(AUTH_HEADER);

		if (header == null || header.isEmpty())
			throw new AuthenticationException("Unauthorised Access. Authorization header is empty");

		String credential = header.trim();
		String[] components = authenticationService.decodeBase64(credential);
	    
//		if (components.length != 2)
//			throw new AuthenticationException("Unauthorised Access. Malformat Authorization content...!");

		return components;
	}

	private boolean checkRequestHeaders(HttpServletRequest request, boolean isAuthNotRequired) {
		log.info("checking headers...");

		List<Boolean> status = new ArrayList<>();
		List<String> headerNames = Collections.list(request.getHeaderNames());

		for (String headerName : headerNames) {
			for (String header : HEADERS) {
				if (header.equalsIgnoreCase(headerName))
					status.add(true);
			}
		}

		if (isAlreadyContainToken(request, isAuthNotRequired, status.size(), headerNames))
			throw new AlreadyLoggedInException("You already have a token with another authority.");

		if (!isAuthNotRequired)
			return status.size() == 2;
		else
			return true;
	}

	private boolean isAlreadyContainToken(HttpServletRequest request, boolean isAuthNotRequired, int statusSize,
			List<String> headerNames) {
		return (isAuthNotRequired && statusSize == 2 && hasAuthorization(headerNames)
				&& request.getRequestURI().equals(LOGIN_URL));
	}

	private boolean hasAuthorization(List<String> headers) {
		boolean status = false;
		for (String headerName : headers) {
			if (headerName.equalsIgnoreCase(AUTH_HEADER)) {
				status = true;
				break;
			}
		}
		return status;
	}

	private boolean isAuthorisedIP(String clientIPAddress) {
		// TODO: Code to check if the current user's Merchant IP address is whitelisted
		// or not
		return true;
	}

	private String getClientIP() {
		return null;
	}
}
