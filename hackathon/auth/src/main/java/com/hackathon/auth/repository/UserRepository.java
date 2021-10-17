package com.hackathon.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hackathon.auth.domain.Role;
import com.hackathon.auth.domain.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

	public UserEntity findByUsername(String userName);

	@Query(value = "Select u from com.hackathon.auth.domain.UserEntity u where u.username =  :username and u.password = :password")
	public UserEntity findByCredentials(String username, String password);

	@Query(value = "Select  r from com.hackathon.auth.domain.UserEntity u "
			+ " inner join com.hackathon.auth.domain.Role r " + " on u.role.id = r.id"
					+ " where u.id = :userId ")
	public Role fetchRoleByUser(Long userId);

}
