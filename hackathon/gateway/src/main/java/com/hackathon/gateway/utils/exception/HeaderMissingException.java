package com.hackathon.gateway.utils.exception;


public class HeaderMissingException extends RuntimeException {

    private static final long serialVersionUID = 5861310537366163L;

    public HeaderMissingException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public HeaderMissingException(final String message) {
        super(message);
    }

    public HeaderMissingException(final Throwable cause) {
        super(cause);
    }
}
