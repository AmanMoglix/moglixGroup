package com.hackathon.auth.model.co;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class UserCO {

//	    private String mobileNumber;

	   
	    private String username; //email

	   
	    private String password;

	  
	    private String firstName;

	    private String lastName;

	    
	    private String role;
	    

		public UserCO(String username, String password, String firstName, String lastName, String role) {
			this.username = username;
			this.password = password;
			this.firstName = firstName;
			this.lastName = lastName;
			this.role = role;
		}
	    public Map<String, Object> getUser() {
	        Map<String, Object> user = new HashMap<>();
	        //user.put("mobileNumber", this.getMobileNumber());
	        user.put("username", this.getUsername());
	        user.put("password", this.getPassword());
	        user.put("firstName", this.getFirstName());
	        user.put("lastName", this.getLastName());
	        return user;
	    }	
}
