package com.hackathon.gateway.service;

import com.hackathon.gateway.model.Token;

public interface TokenService {

    public Token decode(String tokenSource);

    public String decrypt(String data);
}
