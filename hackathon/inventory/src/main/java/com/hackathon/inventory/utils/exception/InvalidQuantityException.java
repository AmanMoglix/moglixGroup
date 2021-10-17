package com.hackathon.inventory.utils.exception;

public class InvalidQuantityException extends RuntimeException {

    private static final long serialVersionUID = 5861310537366163L;

    public InvalidQuantityException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public InvalidQuantityException(final String message) {
        super(message);
    }

    public InvalidQuantityException(final Throwable cause) {
        super(cause);
    }
}
