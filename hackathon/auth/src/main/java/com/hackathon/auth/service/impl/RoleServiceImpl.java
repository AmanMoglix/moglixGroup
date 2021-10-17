package com.hackathon.auth.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hackathon.auth.domain.Role;
import com.hackathon.auth.repository.RoleRepository;
import com.hackathon.auth.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public Role findByAuthority(String authority) {
		return roleRepository.findByAuthority(authority);
	}

	@Override
	public Role get(Long id) {
		return roleRepository.getById(id);
	}

	@Override
	public List<Role> list() {
		return roleRepository.findAll();
	}

	@Override
	public boolean isRoleExist(String authority) {
		return (this.findByAuthority(authority) != null);
	}

	@Override
	public void save(String role) {
		if (!(role != null && role.isEmpty()))
			roleRepository.save(new Role(role));
		else
			throw new IllegalArgumentException("Role input can not be a empty string.");

	}

	@Override
	public void save(List<String> roles) {
		List<Role> roleEntities = new ArrayList<>();
		for (String role : roles) {
			roleEntities.add(new Role(role));
		}
		roleRepository.saveAll(roleEntities);

	}

}
