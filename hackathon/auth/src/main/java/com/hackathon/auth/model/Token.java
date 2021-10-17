package com.hackathon.auth.model;

import lombok.Data;

@Data
public class Token {
	private String role;
	private Long userId;
	private Long userRefer;
	private String username;// email
	private String firstname;
	private String latname;
	private int expiryInMinutes;
}
