package com.hackathon.auth.service;

import com.hackathon.auth.domain.UserEntity;
import com.hackathon.auth.model.CurrentUser;
import com.hackathon.auth.model.co.LoginCO;
import com.hackathon.auth.model.co.UserCO;
import com.hackathon.auth.model.dto.TokenDTO;

public interface UserService {
	public String save(UserCO userCO);

	public UserEntity getUserByUsername(String userName);

	public UserEntity saveEmployee(UserCO userCO, CurrentUser currentUser);

	public boolean isExistByUsername(String username);

	public UserEntity get(Long empId, CurrentUser currentUser);

	public TokenDTO findByCredentials(LoginCO loginCO);
}
