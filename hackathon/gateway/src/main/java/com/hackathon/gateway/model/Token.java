package com.hackathon.gateway.model;


public class Token {
	private String role;
	private Long userId;
	private Long userRefer;
	private String username;// email
	private String firstname;
	private String latname;
	private int expiryInMinutes;
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getUserRefer() {
		return userRefer;
	}
	public void setUserRefer(Long userRefer) {
		this.userRefer = userRefer;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLatname() {
		return latname;
	}
	public void setLatname(String latname) {
		this.latname = latname;
	}
	public int getExpiryInMinutes() {
		return expiryInMinutes;
	}
	public void setExpiryInMinutes(int expiryInMinutes) {
		this.expiryInMinutes = expiryInMinutes;
	}

//    private LocationDTO location;
//
//    private Date loginTime;

   
}
