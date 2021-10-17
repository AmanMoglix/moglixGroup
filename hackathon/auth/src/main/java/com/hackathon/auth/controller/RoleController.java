package com.hackathon.auth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hackathon.auth.domain.Role;
import com.hackathon.auth.service.RoleService;
import com.hackathon.auth.utils.exception.DuplicateRecordException;

@RestController
@RequestMapping("/role")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public void save(@RequestBody Role roleCO) {
//        if (bindingResult.hasErrors())
//            throw new BadRequestException("Bad Request.");

		if (roleService.isRoleExist(roleCO.getAuthority()))
			throw new DuplicateRecordException("This role already exists in our database");

		roleService.save(roleCO.getAuthority());
	}

	@RequestMapping(value = "/view", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public List<Role> get() {
		return roleService.list();
	}
}
