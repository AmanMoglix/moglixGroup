package com.hackathon.catalog.model;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.hackathon.catalog.enumerations.ApplicationRole;
import com.hackathon.catalog.utils.exception.AuthenticationException;
import com.hackathon.catalog.utils.exception.BadRequestException;


import lombok.Data;
@Data
public class CurrentUser {
	private String userId;
	private String username;
	private ApplicationRole authority;

	private Map<String, Object> location;
	 private CurrentUser(String userId, String username, ApplicationRole authority, Map<String, Object> location) {
	        this.userId = userId;
	        this.username = username;
	        this.authority = authority;
	        this.location = location;
	    }
	 private CurrentUser(String userId, String username, ApplicationRole authority) {
	        this.userId = userId;
	        this.username = username;
	        this.authority = authority;
	       
	    }
	 public static CurrentUser getInstance(String userId, String username, String authority, String location) {
	        //verifyJSON(location);

		CurrentUser currentUser = new CurrentUser(userId, username,
				ApplicationRole.from(authority)/* , new JSONObject(location).toMap() */);
	        if (!currentUser.getAuthority().isVerified())
	            throw new AuthenticationException("Unauthorised to access this resource.");

	        return currentUser;
	    }
	 private static void verifyJSON(String location) {
	        if (!isVerifiedJSON(location))
	            throw new BadRequestException("Invalid Request Header JSON");
	    }

	    private static boolean isVerifiedJSON(String stringJSON) {
	        try {
	            new Gson().fromJson(new JSONObject(stringJSON).toString(), Object.class);
	            return true;
	        } catch (JsonSyntaxException | JSONException jsonException) {
	            return false;
	        }
	    }
}
