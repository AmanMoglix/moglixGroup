package com.hackathon.gateway.service.impl;



import java.io.UnsupportedEncodingException;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hackathon.gateway.model.Token;
import com.hackathon.gateway.service.AuthenticationService;
import com.hackathon.gateway.service.TokenService;
import com.hackathon.gateway.utils.exception.AuthenticationException;
import com.hackathon.gateway.utils.exception.InvalidTokenException;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    @Autowired
    private TokenService tokenService;

    @Override
    public Token authenticate(String authToken) {
        logger.info("Decoding JWT token '{}'", authToken);
        try {
            return tokenService.decode(authToken);
        } catch (Exception ex) {
            logger.error(" Exception Occurred : {} ", ex);
            throw new InvalidTokenException("Invalid token unauthorised access.");
        }
    }

    @Override
    public String[] decodeBase64(String encodedBase64) {
        logger.info("Decoding base64 content '{}'", encodedBase64);
        String[] encoded = encodedBase64.split("\\.");
       // String[] encoded = encodedBase64.split(" ");
//        if (encoded.length != 2)
//            throw new AuthenticationException("Unauthorised Access. Malformat Authorization content");
        Base64.Decoder decoder = Base64.getDecoder();
//        //String header = new String(decoder.decode(encoded[0]));
//        System.out.println(header);
        String payload = new String(decoder.decode(encoded[1]));
        System.out.println("*********"+ payload);
       // byte[] actualByte = Base64.getDecoder().decode(encoded[1]);
        byte[] actualByte =decoder.decode(encoded[1]) ;
     
       return new String(actualByte).split(":");
    }
}
