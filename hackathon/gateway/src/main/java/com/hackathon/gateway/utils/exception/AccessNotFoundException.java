package com.hackathon.gateway.utils.exception;

public class AccessNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 5861310537366163L;

    public AccessNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public AccessNotFoundException(final String message) {
        super(message);
    }

    public AccessNotFoundException(final Throwable cause) {
        super(cause);
    }

}
