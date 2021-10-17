package com.hackathon.auth.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hackathon.auth.config.ApplicationConfig;
import com.hackathon.auth.model.Token;
import com.hackathon.auth.service.TokenService;
import com.hackathon.auth.utils.exception.InvalidTokenException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenServiceImpl implements TokenService {
	private static final Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);
    private final String AES_KEY = "TOKEN_SECURITY_MOGLIX_AES_KEY_IN_JWT";
    
    @Autowired
	private ApplicationConfig config;
    
	@Override
	public Token decode(String tokenSource) {
		try {
            AES aes=new AES(AES_KEY);
            Claims body = Jwts.parser()
                    .setSigningKey(config.getSecret().getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(tokenSource)
                    .getBody();

            Token token = new Token();
            token.setRole(String.valueOf(body.get("authority")));
            token.setUserId(Long.valueOf(String.valueOf(body.get("userId"))));
            token.setUserRefer(Long.valueOf(aes.decrypt(String.valueOf(body.get("userRefer")))));
            token.setUsername(String.valueOf(body.get("username")));
//            token.setMobile(String.valueOf(body.get("mobile")));
            token.setFirstname(String.valueOf(body.get("firstname")));
            token.setLatname(String.valueOf(body.get("lastname")));
//            if (body.containsKey("location"))
//                token.setLocation(modelMapper.map(body.get("location"), LocationDTO.class));
//            token.setLoginTime(new Date(Long.valueOf(String.valueOf(body.get("loginTime")))));
			token.setExpiryInMinutes(Integer.parseInt(String.valueOf(body.get("expiry"))));

            return token;
        } catch (Exception exception) {
            logger.error("Unable to validateAndParseJWT token ", exception);
            throw new InvalidTokenException("Unable to decode token, reason : " + exception.getMessage());
        }
	}

	@Override
	public String encode(Token token) {
		AES aes=new AES(AES_KEY);
		Claims claims=Jwts.claims();
		claims.put("authority", token.getRole());
        claims.put("userId", token.getUserId());
        claims.put("userRefer", aes.encrypt(String.valueOf(token.getUserRefer())));
        claims.put("username", token.getUsername());
        claims.put("firstname",token.getFirstname());
        claims.put("lastname",token.getLatname());
        claims.put("expiry", config.getExpiryInMinutes());
		return Jwts.builder()
				.setClaims(claims)
				.signWith(SignatureAlgorithm.HS512, config.getSecret().getBytes(StandardCharsets.UTF_8))
                .compact();
	}

	@Override
	public String encrypt(String data) {
		AES aes = new AES(AES_KEY);
        return aes.encrypt(data);
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
                key = secret.getBytes(StandardCharsets.ISO_8859_1);
                sha = MessageDigest.getInstance("SHA-1");
                key = sha.digest(key);
                key = Arrays.copyOf(key, 16);
                secretKey = new SecretKeySpec(key, "AES");
            } catch (NoSuchAlgorithmException e) {
                logger.error("Error in setting key", e);
            }
        }

        String encrypt(String strToEncrypt) {
            try {
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.ISO_8859_1)));
            } catch (Exception e) {
                logger.error("Error in AES.encrypt", e);
            }
            return null;
        }

        String decrypt(String strToDecrypt) {
            try {
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                cipher.init(Cipher.DECRYPT_MODE, secretKey);
                return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
            } catch (Exception e) {
                logger.error("Error in AES.decrypt", e);
            }
            return null;
        }
    }
}
