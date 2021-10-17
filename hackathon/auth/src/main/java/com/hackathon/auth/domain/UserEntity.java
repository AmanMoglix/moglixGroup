package com.hackathon.auth.domain;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import lombok.Data;
@Data
@Entity
@Table(name = "user_entity")
public class UserEntity {
	private static final Logger logger=LoggerFactory.getLogger(UserEntity.class);
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "user_name")
	private String username;
	
	@Column(name = "user_password")
	private String password;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "token", nullable = true)
    private String token;//can be used for resetPassword link etc..
	@OneToOne
    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "FK_user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "FK_role_id", referencedColumnName = "id")})
    private Role role;
//	private Integer defaultLoginBranch;
//	private Boolean isFirstLogin;
	
	public static UserEntity bind(Map<String, Object> userMap) {
        logger.info("Binding user data in user Obj from map {}", userMap);

        UserEntity user = new UserEntity();
        user.setUsername(String.valueOf(userMap.get("username")));
        user.setPassword(generateMd5(String.valueOf(userMap.get("password"))));
       // user.setUserId(generateUserId());
     //   user.setMobileNumber(String.valueOf(userMap.get("mobileNumber")));
        user.setFirstName(String.valueOf(userMap.get("firstName")));
        user.setLastName(String.valueOf(userMap.get("lastName")));
        //user.setLocationApproved(true);

        if (String.valueOf(userMap.get("lastName")) == null || String.valueOf(userMap.get("lastName")).isEmpty())
            user.setLastName(null);
        else
            user.setLastName(String.valueOf(userMap.get("lastName")));

        return user;
    }
	
	  public final static String generateMd5(String input) {
	        try {
	        	logger.info("Generating hash for password {}", input);

	            MessageDigest md = MessageDigest.getInstance("MD5");
	            byte[] messageDigest = md.digest(input.getBytes());
	            BigInteger no = new BigInteger(1, messageDigest);
	            String hashText = no.toString(16);
	            while (hashText.length() < 32) {
	                hashText = "0" + hashText;
	            }
	            return hashText;
	        } catch (NoSuchAlgorithmException e) {
	        	logger.error("No such algo exception occurred during Generating hash for password");
	            throw new RuntimeException(e);
	        }
	    }
}
