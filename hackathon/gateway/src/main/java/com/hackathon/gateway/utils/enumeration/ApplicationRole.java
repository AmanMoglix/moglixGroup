package com.hackathon.gateway.utils.enumeration;



import java.util.LinkedList;
import java.util.List;

import com.hackathon.gateway.utils.exception.RoleNotFoundException;

public enum ApplicationRole {

    SUPER_ADMIN("ROLE_SUPER_ADMIN"),
    ADMIN("ROLE_ADMIN"),
    CUSTOMER("ROLE_CUSTOMER"),
    SALE("ROLE_SALES_PERSON"),
    MANAGER("ROLE_MANAGER");

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
            throw new RoleNotFoundException("Given token has not a valid role to access this resource");
    }

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return role;
    }

    public static List<ApplicationRole> getApplicationRoles() {
        List<ApplicationRole> roles = new LinkedList<ApplicationRole>();
        roles.add(SUPER_ADMIN);
        roles.add(ADMIN);
        roles.add(CUSTOMER);
        roles.add(SALE);
        roles.add(MANAGER);
        return roles;
    }
}
