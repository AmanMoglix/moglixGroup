package com.hackathon.auth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component("config")
public class ApplicationConfig {
	@Value("${hackathon.token.secret}")
	private String secret;

	@Value("${hackathon.token.expiry}")
	private int expiryInMinutes;
	
	@Value("${hackathon.server.ip}")
	private String serverIp;

	@Value("${hackathon.gateway.server.port}")
	private String serverPort;

//	    @Value("${hackathon.token.expiry}")
//	    private int expiryInMinutes;

	@Value("${hackathon.base.url}")
	private String baseUrl;

	@Value("${hackathon.forget.password.ui.path}")
	private String forgetPasswordPath;

}
