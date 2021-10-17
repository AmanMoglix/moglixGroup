package com.hackathon.auth.model.dto;

import lombok.Data;

@Data
public class TokenDTO {
private String token;

public TokenDTO(String token) {
	super();
	this.token = token;
}

public TokenDTO() {
	super();
}

}
