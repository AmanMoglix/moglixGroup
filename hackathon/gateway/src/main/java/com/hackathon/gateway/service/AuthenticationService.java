package com.hackathon.gateway.service;

import com.hackathon.gateway.model.Token;

public interface AuthenticationService {

    public Token authenticate(String authToken);

    public String[] decodeBase64(String encodedBase64);
}
