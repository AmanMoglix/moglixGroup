package com.hackathon.gateway.utils.exception;


public class MediaNotSupportedException extends RuntimeException {

    private static final long serialVersionUID = 5861310537366163L;

    public MediaNotSupportedException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public MediaNotSupportedException(final String message) {
        super(message);
    }

    public MediaNotSupportedException(final Throwable cause) {
        super(cause);
    }
}
