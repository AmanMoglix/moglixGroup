package com.hackathon.catalog.enumerations;

public enum EmployeeType {
    SALES("SALES"),
    MANAGER("MANAGER"),
    ERROR("Error");

    private String type;

    EmployeeType(String type) {
        this.type = type;
    }

    public static EmployeeType from(String type) {
        if (type.equalsIgnoreCase("sales"))
            return SALES;
        else if (type.equalsIgnoreCase("manager"))
            return MANAGER;
        else
            return ERROR;
    }

    public static EmployeeType from(ApplicationRole role) {
        if (role.equals(ApplicationRole.MANAGER))
            return MANAGER;
        else if (role.equals(ApplicationRole.SALE))
            return SALES;
        else
            return ERROR;
    }

    public String getType() {
        return type;
    }
}
