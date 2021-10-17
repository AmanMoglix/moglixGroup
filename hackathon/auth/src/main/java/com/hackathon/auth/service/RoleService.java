package com.hackathon.auth.service;

import java.util.List;

import com.hackathon.auth.domain.Role;



public interface RoleService {
    public Role findByAuthority(String authority);

    public Role get(Long id);

    public List<Role> list();

    public boolean isRoleExist(String authority);

    public void save(String role);

    public void save(List<String> roles);
}
