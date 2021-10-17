package com.hackathon.auth.model;

public class UserLoginRequestModel {
	private String username;
	private String password;
	private Integer tenantOrClientId;
	private String OTP;
	private boolean resendOTP;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getTenantOrClientId() {
		return tenantOrClientId;
	}
	public void setTenantOrClientId(Integer tenantOrClientId) {
		this.tenantOrClientId = tenantOrClientId;
	}
	public String getOTP() {
		return OTP;
	}
	public void setOTP(String oTP) {
		OTP = oTP;
	}
	public boolean isResendOTP() {
		return resendOTP;
	}
	public void setResendOTP(boolean resendOTP) {
		this.resendOTP = resendOTP;
	}
}
