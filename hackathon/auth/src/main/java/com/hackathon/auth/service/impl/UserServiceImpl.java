package com.hackathon.auth.service.impl;

import java.util.Map;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.hackathon.auth.domain.Role;
import com.hackathon.auth.domain.UserEntity;
import com.hackathon.auth.enumeration.ApplicationRole;
import com.hackathon.auth.model.CurrentUser;
import com.hackathon.auth.model.co.LoginCO;
import com.hackathon.auth.model.co.UserCO;
import com.hackathon.auth.model.dto.TokenDTO;
import com.hackathon.auth.model.dto.UserDTO;
import com.hackathon.auth.repository.RoleRepository;
import com.hackathon.auth.repository.UserRepository;
import com.hackathon.auth.service.TokenService;
import com.hackathon.auth.service.UserService;
import com.hackathon.auth.utils.exception.NotFoundException;




@Service
public class UserServiceImpl implements UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private RoleRepository roleRepository;
//	@Autowired
//	BCryptPasswordEncoder bCryptPasswordEncoder;
	 private Map<String, Object> getUserWithRoleMap(UserCO userCO) {
	        Map<String, Object> user = userCO.getUser();
	        user.put("role", roleRepository.findByAuthority(userCO.getRole()));
	        return user;
	    }
	@Override
	public String save(UserCO userCO) {
		logger.info("Going to persist user by '{}'", new Gson().toJson(userCO));
		UserEntity user=UserEntity.bind(this.getUserWithRoleMap(userCO));
		//Role role=(Role)user.get
		return this.userRepository.save(user).getFirstName();
	}

	@Override
	public UserEntity getUserByUsername(String userName) {
		UserEntity user = this.userRepository.findByUsername(userName);
		if (user != null) {
			logger.info("Getting user by user name :" + userName);
			return user;
		}
		return null;
	}

	@Override
	public UserEntity saveEmployee(UserCO userCO, CurrentUser currentUser) {
		logger.info("Going to persist employee by '{}'", new Gson().toJson(userCO));
		UserEntity user=UserEntity.bind(this.getUserWithRoleMap(userCO));
		return this.userRepository.save(user);
	}

	@Override
	public boolean isExistByUsername(String username) {
		return (this.userRepository.findByUsername(username) != null);
	}

	@Override
	public UserEntity get(Long empId, CurrentUser currentUser) {
		UserEntity user = this.userRepository.getById(empId);
		return user;
	}

	@Override
	public TokenDTO findByCredentials(LoginCO loginCO) {
		logger.info("finding the user by username and password '{}'", loginCO.getUsername());
		String password=UserEntity.generateMd5(loginCO.getPassword());
		UserEntity user=this.userRepository.findByCredentials(loginCO.getUsername(),password);
		if (user == null)
            throw new NotFoundException("User not found by " + loginCO.getUsername());
		
		Role role = userRepository.fetchRoleByUser(user.getId());
		//Role role=user.getRole();
        if (role == null)
            throw new NotFoundException("Role not found by " + loginCO.getUsername());
        
        logger.info("finding the user role while login '{}'",role.getAuthority());
        
        UserDTO userDTO=this.modelMapper.map(user,UserDTO.class);
        userDTO.setAuthority(role.getAuthority());
        
        if (ApplicationRole.ADMIN.getRole().contentEquals(userDTO.getAuthority()) || ApplicationRole.SUPER_ADMIN.getRole().contentEquals(userDTO.getAuthority()));
        
		return new TokenDTO(this.tokenService.encode(userDTO.bindToken(user.getId())));
	}

}
