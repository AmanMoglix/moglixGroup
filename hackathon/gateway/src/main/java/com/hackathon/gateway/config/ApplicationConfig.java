package com.hackathon.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("config")
public class ApplicationConfig {

    @Value("${hackathon.token.secret}")
    private String secret;

    public String getSecret() {
        return secret;
    }

}
