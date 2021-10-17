package com.hackathon.auth.model.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hackathon.auth.model.Token;

import lombok.Data;
@Data
public class UserDTO {
	private Long userId;

    private String mobileNumber;

    private String username; //email

    private String firstName;

    private String lastName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String authority;
    
    @JsonProperty("joiningDate")
    private Date dateCreated;
    
    public Token bindToken(Long userRefer) {
        Token token = new Token();
        token.setUserId(this.userId);
       // token.setMobile(this.mobileNumber);
        token.setUsername(this.username);
        token.setFirstname(this.firstName);
        token.setLatname(this.lastName);
        token.setUserRefer(userRefer);
        //token.setLoginTime(new Date());
        token.setRole(this.getAuthority());
        //token.setLocation(this.location);
        return token;
    }
}
