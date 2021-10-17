package com.hackathon.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hackathon.auth.domain.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	
	@Query(value = "Select r from com.hackathon.auth.domain.Role r where r.authority = :role ")
	public Role findByAuthority(String role);

}
