package com.hackathon.catalog.utils.exception;

public class InvalidVersionException extends RuntimeException {

    private static final long serialVersionUID = 5861310537366163L;

    public InvalidVersionException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public InvalidVersionException(final String message) {
        super(message);
    }

    public InvalidVersionException(final Throwable cause) {
        super(cause);
    }

}
