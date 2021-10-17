package com.hackathon.auth.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
@Data
@Entity
@Table(name="hackathon_role")
public class Role {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String authority;
    
    public Role(String role) {
	this.authority=role;	
   	}

	public Role() {
	}
    
}
