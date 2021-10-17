package com.hackathon.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hackathon.auth.domain.UserEntity;
import com.hackathon.auth.model.CurrentUser;
import com.hackathon.auth.model.co.LoginCO;
import com.hackathon.auth.model.co.UserCO;
import com.hackathon.auth.model.dto.TokenDTO;
import com.hackathon.auth.service.UserService;
import com.hackathon.auth.utils.exception.AuthenticationException;
import com.hackathon.auth.utils.exception.DuplicateRecordException;

@RestController
@RequestMapping(value = "/api")
public class UserController {
	@Autowired
	private UserService userService;

	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public UserEntity register(@RequestBody UserCO userCO, @RequestHeader("X_USER_ID") String currentUserId,
			@RequestHeader("X_USERNAME") String username, @RequestHeader("X_AUTHORITY") String authority,
			@RequestHeader("X_LOCATION") String location) {

		CurrentUser currentUser = CurrentUser.getInstance(currentUserId, username, authority, location);

		if (userService.isExistByUsername(userCO.getUsername()))
			throw new DuplicateRecordException(
					"This user is already exists in our database with this username " + userCO.getUsername());
		return userService.saveEmployee(userCO, currentUser);
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public TokenDTO signIn( @RequestBody  LoginCO loginCO) {

        if (!userService.isExistByUsername(loginCO.getUsername()))
            throw new AuthenticationException("Username is not registered with us");

        return userService.findByCredentials(loginCO);
    }
}
