//package com.hackathon.auth.utils.security;
//
//import java.util.Arrays;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.hackathon.auth.domain.UserEntity;
//import com.hackathon.auth.repository.UserRepository;
//
//@Service
//public class JwtUserDetailsService implements UserDetailsService{
//	@Autowired
//	UserRepository userRepository;
//	@Override
//	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
//		UserEntity user = userRepository.findByUsername(userName);
//		System.out.println(user);
//		if (null == user) {
//			throw new UsernameNotFoundException("Invalid user name or password.");
//		}
//		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
//				getAuthority());
//	}
//
//	private List<SimpleGrantedAuthority> getAuthority() {
//		return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
//	}
//
//}
