package com.hackathon.gateway.utils.exception;


public class LevelNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 5861310537366163L;

    public LevelNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public LevelNotFoundException(final String message) {
        super(message);
    }

    public LevelNotFoundException(final Throwable cause) {
        super(cause);
    }

}
