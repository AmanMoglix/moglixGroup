package com.hackathon.gateway.utils.exception;

public class RoleNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 5861310537366163L;

    public RoleNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public RoleNotFoundException(final String message) {
        super(message);
    }

    public RoleNotFoundException(final Throwable cause) {
        super(cause);
    }

}
