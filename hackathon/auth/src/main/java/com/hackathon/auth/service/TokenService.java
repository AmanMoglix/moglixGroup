package com.hackathon.auth.service;

import com.hackathon.auth.model.Token;

public interface TokenService {
	   public Token decode(String tokenSource);

	    public String encode(Token token);

	    public String encrypt(String data);

	    public String decrypt(String data);
}
