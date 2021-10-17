package com.hackathon.catalog.model.dto.discovery;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
public class UserDiscoveryDTO {

    private String userId;
    
    private String username; //email

    private String firstName;

    private String lastName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String authority;
}
