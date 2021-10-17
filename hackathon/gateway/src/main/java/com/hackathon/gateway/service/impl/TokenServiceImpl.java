package com.hackathon.gateway.service.impl;




import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hackathon.gateway.config.ApplicationConfig;
import com.hackathon.gateway.model.Token;
import com.hackathon.gateway.model.dto.LocationDTO;
import com.hackathon.gateway.service.TokenService;
import com.hackathon.gateway.utils.exception.InvalidTokenException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class TokenServiceImpl implements TokenService {

    private static final Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);

    private final String AES_KEY = "TOKEN_SECURITY_MOGLIX_AES_KEY_IN_JWT";

    @Autowired
    private ApplicationConfig config;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Token decode(String tokenSource) {
        try {
            AES aes = new AES(AES_KEY);
            Claims body = Jwts.parser()
                    .setSigningKey(config.getSecret().getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(tokenSource)
                    .getBody();

            Token token = new Token();
           token.setRole(String.valueOf(body.get("authority")));
           token.setUserId(Long.valueOf(String.valueOf(body.get("userId"))));
           token.setUserRefer(Long.valueOf(aes.decrypt(String.valueOf(body.get("userRefer")))));
           token.setUsername(String.valueOf(body.get("username")));
           ///token.setMobile(String.valueOf(body.get("mobile")));
           token.setFirstname(String.valueOf(body.get("firstname")));
           token.setLatname(String.valueOf(body.get("lastname")));
////            if (body.containsKey("location"))
////                token.setLocation(modelMapper.map(body.get("location"), LocationDTO.class));
////            token.setLoginTime(new Date(Long.valueOf(String.valueOf(body.get("loginTime")))));
            token.setExpiryInMinutes(Integer.parseInt(String.valueOf(body.get("expiry"))));

            return token;
        } catch (Exception exception) {
            logger.error("Unable to validateAndParseJWT token ", exception);
            throw new InvalidTokenException("Unable to decode token, reason : " + exception.getMessage());
        }
    }

    @Override
    public String decrypt(String data) {
        AES aes = new AES(AES_KEY);
        return aes.decrypt(data);
    }

    private class AES {

        private SecretKeySpec secretKey;

        private byte[] key;

        AES(String secret) {
            MessageDigest sha = null;
            try {
                key = secret.getBytes(StandardCharsets.UTF_8);
                sha = MessageDigest.getInstance("SHA-1");
                key = sha.digest(key);
                key = Arrays.copyOf(key, 16);
                secretKey = new SecretKeySpec(key, "AES");
            } catch (NoSuchAlgorithmException e) {
                logger.error("Error in setting key", e);
            }
        }

        String decrypt(String strToDecrypt) {
            try {
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
                cipher.init(Cipher.DECRYPT_MODE, secretKey);
                return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
            } catch (Exception e) {
                logger.error("Error in AES.decrypt", e);
            }
            return null;
        }
    }
}
