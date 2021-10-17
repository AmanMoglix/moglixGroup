package com.hackathon.gateway.utils.exception;


public class InvalidTokenException extends RuntimeException {

    private static final long serialVersionUID = 5861310537366163L;

    public InvalidTokenException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public InvalidTokenException(final String message) {
        super(message);
    }

    public InvalidTokenException(final Throwable cause) {
        super(cause);
    }
}
