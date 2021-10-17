package com.hackathon.auth.model.dto;

import java.io.Serializable;

public class AuthResponse implements Serializable {
	private static final long serialVersionUID = -7491368628697805503L;
	private String userName;
	private boolean firstLogin;
	private String token;

	public AuthResponse(String userName, String token, boolean firstLogin) {
		this.userName = userName;
		this.firstLogin = firstLogin;
		this.token = token;
	}

	public String getUserName() {
		return userName;
	}

	public AuthResponse setUserName(String userName) {
		this.userName = userName;
		return this;
	}

	public String getToken() {
		return token;
	}

	public AuthResponse setToken(String token) {
		this.token = token;
		return this;
	}

	public boolean isFirstLogin() {
		return firstLogin;
	}

	public void setFirstLogin(boolean firstLogin) {
		this.firstLogin = firstLogin;
	}
}
