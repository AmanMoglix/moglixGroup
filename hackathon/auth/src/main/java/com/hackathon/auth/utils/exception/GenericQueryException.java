package com.hackathon.auth.utils.exception;

public class GenericQueryException extends RuntimeException {

    private static final long serialVersionUID = 5861310537366163L;

    public GenericQueryException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public GenericQueryException(final String message) {
        super(message);
    }

    public GenericQueryException(final Throwable cause) {
        super(cause);
    }

}
