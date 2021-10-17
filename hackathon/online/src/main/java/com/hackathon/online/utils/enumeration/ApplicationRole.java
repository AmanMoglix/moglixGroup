package com.hackathon.online.utils.enumeration;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.hackathon.online.utils.exceptions.NotFoundException;

public enum ApplicationRole {

    SUPER_ADMIN("ROLE_SUPER_ADMIN"), //1
    ADMIN("ROLE_ADMIN"), //2
    MANAGER("ROLE_MANAGER"),//3
    SALE("ROLE_SALES_PERSON"),//4
    CUSTOMER("ROLE_CUSTOMER");

    private final String role;

    ApplicationRole(String role) {
        this.role = role;
    }

    public static ApplicationRole from(String authority) {
        if (authority.equalsIgnoreCase("role_super_admin"))
            return SUPER_ADMIN;
        if (authority.equalsIgnoreCase("role_admin"))
            return ADMIN;
        else if (authority.equalsIgnoreCase("role_customer"))
            return CUSTOMER;
        else if (authority.equalsIgnoreCase("role_sales_person"))
            return SALE;
        else if (authority.equalsIgnoreCase("role_manager"))
            return MANAGER;
        else
            throw new NotFoundException("role not found");
    }

    public static List<ApplicationRole> getApplicationRoles() {
        List<ApplicationRole> roles = new LinkedList<ApplicationRole>();
        roles.add(SUPER_ADMIN);
        roles.add(ADMIN);
        roles.add(MANAGER);
        roles.add(SALE);
        roles.add(CUSTOMER);
        return roles;
    }

    public String getRole() {
        return role;
    }

    public int getHierarchyDigit() {
        if (this.role.equalsIgnoreCase("role_super_admin")) {
            return 1;
        } else if (this.role.equalsIgnoreCase("role_admin")) {
            return 2;
        } else if (this.role.equalsIgnoreCase("role_manager")) {
            return 3;
        } else {
            return 4;
        }
    }

    public boolean isVerified() {
        return getApplicationRoles().contains(this);
    }

    public List<Integer> getHierarchy() {
        List<Integer> roles = new ArrayList<>();
        if (this.role.equalsIgnoreCase("role_super_admin")) {
            roles.add(2);
            roles.add(3);
            roles.add(4);
        } else if (this.role.equalsIgnoreCase("role_admin")) {
            roles.add(3);
            roles.add(4);
        } else if (this.role.equalsIgnoreCase("role_manager")) {
            roles.add(4);
        }

        return roles;
    }

    @Override
    public String toString() {
        return role;
    }
}
