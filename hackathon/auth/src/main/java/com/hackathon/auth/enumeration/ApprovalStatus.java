package com.hackathon.auth.enumeration;

public enum ApprovalStatus {

    APPROVED("APPROVED"),
    REJECTED("REJECTED"),
    NOT_ASSIGNED("NOT_ASSIGNED"),
    WAITING("WAITING"),
    ERROR("ERROR");

    private final String status;

    ApprovalStatus(String status) {
        this.status = status;
    }

    public static ApprovalStatus from(String status) {
        if (status.equalsIgnoreCase("APPROVED"))
            return APPROVED;
        else if (status.equalsIgnoreCase("REJECTED"))
            return REJECTED;
        else if (status.equalsIgnoreCase("NOT_ASSIGNED") || status.equalsIgnoreCase("NOT ASSIGNED"))
            return NOT_ASSIGNED;
        else if (status.equalsIgnoreCase("WAITING"))
            return WAITING;
        else
            return ERROR;
    }

    public String getStatus() {
        return status;
    }
}
