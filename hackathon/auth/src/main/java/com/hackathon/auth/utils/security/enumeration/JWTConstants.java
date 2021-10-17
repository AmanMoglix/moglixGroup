package com.hackathon.auth.utils.security.enumeration;

public class JWTConstants {
	public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 5 * 60 * 60;
	public static final Long USER_REFER=5L;
	public static final String SIGNING_KEY = "TOKEN_SECURITY_MOGLIX_AES_KEY_IN_JWT";
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
}
