//package com.hackathon.auth.controller;
//
//import java.io.Serializable;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.hackathon.auth.config.ApplicationConfig;
//import com.hackathon.auth.domain.UserEntity;
//import com.hackathon.auth.model.UserLoginRequestModel;
//import com.hackathon.auth.model.dto.AuthResponse;
//import com.hackathon.auth.service.UserService;
//import com.hackathon.auth.utils.security.JwtTokenUtil;
//import com.sun.istack.NotNull;
//@RestController
//@RequestMapping(value="/api/auth")
//@CrossOrigin
//public class AuthenticationController implements Serializable{
//	private static final long serialVersionUID = 6831968981313281239L;
//
//	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);
//	@Autowired
//	private AuthenticationManager authenticationManager;
//	@Autowired
//	private JwtTokenUtil jwtTokenUtil;
//	
//	@Autowired
//	private UserService userService;
//	
//	@Autowired
//	private ApplicationConfig applicationConfig;
//
//	@SuppressWarnings("unlikely-arg-type")
//	@RequestMapping(value = "/login", method = RequestMethod.POST)
//	public ResponseEntity<?> userLogin(HttpServletRequest httpServletRequest,
//			@RequestBody @NotNull UserLoginRequestModel userLoginRequestModel) throws Exception {
//		LOGGER.info("userLogin() method call...");
//		final Authentication authentication=authenticationManager
//				.authenticate(new UsernamePasswordAuthenticationToken(userLoginRequestModel.getUsername(), userLoginRequestModel.getPassword()));
//	SecurityContextHolder.getContext().setAuthentication(authentication);
//	//geting the userDetails after authenticated from the authentication Object
//	UserDetails userDetails =(UserDetails) authentication.getPrincipal();
//	
//	//now get the user from the database userRnity
//	UserEntity userEntity = this.userService.getUserByUsername(userDetails.getUsername());
//	final String token = jwtTokenUtil.generateToken(userDetails.getUsername(),
//			String.valueOf(userLoginRequestModel.getTenantOrClientId()),1,applicationConfig.getSecret());
//	boolean isFirstLogin=true;
//	return ResponseEntity.ok(new AuthResponse(userDetails.getUsername(), token, isFirstLogin));
//
//	}
//}
