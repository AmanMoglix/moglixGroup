package com.hackathon.catalog.utils.exception;

public class DiscoveryServiceException extends RuntimeException {

    private static final long serialVersionUID = 5861310537366163L;

    public DiscoveryServiceException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public DiscoveryServiceException(final String message) {
        super(message);
    }

    public DiscoveryServiceException(final Throwable cause) {
        super(cause);
    }
}
