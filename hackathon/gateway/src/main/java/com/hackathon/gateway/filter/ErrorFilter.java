package com.hackathon.gateway.filter;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.post.SendErrorFilter;
import org.springframework.http.MediaType;

import com.google.gson.GsonBuilder;
import com.hackathon.gateway.utils.exception.AlreadyLoggedInException;
import com.hackathon.gateway.utils.exception.AuthenticationException;
import com.hackathon.gateway.utils.exception.HeaderMissingException;
import com.hackathon.gateway.utils.exception.InvalidTokenException;
import com.hackathon.gateway.utils.exception.MediaNotSupportedException;
import com.hackathon.gateway.utils.exception.RoleNotFoundException;
import com.netflix.zuul.context.RequestContext;
import com.hackathon.gateway.model.Error;
public class ErrorFilter extends SendErrorFilter {

    protected static final String SEND_ERROR_FILTER_RAN = "sendErrorFilter.ran";

    private Logger log = LoggerFactory.getLogger(ErrorFilter.class);

    @Value("/error")
    private String errorPath;

    @Override
    public int filterOrder() {
        return -1;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        return requestContext.getThrowable() != null && !requestContext.getBoolean(SEND_ERROR_FILTER_RAN, false);
    }

    @Override
    public Object run() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        Throwable throwable = requestContext.getThrowable();

        if (throwable.getCause() instanceof AuthenticationException) {
            AuthenticationException authenticationException = (AuthenticationException) throwable.getCause();
            authenticationException(requestContext, authenticationException.getMessage());
        } else if (throwable.getCause() instanceof InvalidTokenException) {
            InvalidTokenException invalidTokenException = (InvalidTokenException) throwable.getCause();
            invalidTokenException(requestContext, invalidTokenException.getMessage());
        } else if (throwable.getCause() instanceof HeaderMissingException) {
            HeaderMissingException headerMissingException = (HeaderMissingException) throwable.getCause();
            headerMissingException(requestContext, headerMissingException.getMessage());
        } else if (throwable.getCause() instanceof MediaNotSupportedException) {
            MediaNotSupportedException mediaNotSupportedException = (MediaNotSupportedException) throwable.getCause();
            mediaNotSupportedException(requestContext, mediaNotSupportedException.getMessage());
        } else if (throwable.getCause() instanceof RoleNotFoundException) {
            RoleNotFoundException roleNotFoundException = (RoleNotFoundException) throwable.getCause();
            roleNotFoundException(requestContext, roleNotFoundException.getMessage());
        } else if (throwable.getCause() instanceof AlreadyLoggedInException) {
            AlreadyLoggedInException alreadyLoggedInException = (AlreadyLoggedInException) throwable.getCause();
            alreadyLoggedInException(requestContext, alreadyLoggedInException.getMessage());
        } else {
            zuulException(requestContext);
        }
        requestContext.set(SEND_ERROR_FILTER_RAN, true);
        return null;
    }

    private void authenticationException(RequestContext requestContext, String message) {
        log.error("Authentication exception Occurred");
        writeErrorResponse(requestContext, HttpStatus.UNAUTHORIZED.value(),
                Error.setErrorResponse(HttpStatus.UNAUTHORIZED.value(), message));
    }

    private void invalidTokenException(RequestContext requestContext,String message) {
        log.error("Invalid Token Exception Occurred");
        writeErrorResponse(requestContext, HttpStatus.UNAUTHORIZED.value(),
                Error.setErrorResponse(HttpStatus.UNAUTHORIZED.value(), message));
    }

    private void zuulException(RequestContext requestContext) {
        log.error("Zuul Exception Occurred", requestContext.getThrowable());
        writeErrorResponse(requestContext, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                Error.setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "An error is occurred at server side due to wrong input. Please try again.."));
    }

    private void headerMissingException(RequestContext requestContext, String message) {
        log.error("Required headers missing  Exception Occurred");
        writeErrorResponse(requestContext, HttpStatus.REQUEST_HEADER_FIELDS_TOO_LARGE.value(),
                Error.setErrorResponse(HttpStatus.REQUEST_HEADER_FIELDS_TOO_LARGE.value(),
                        message));
    }

    private void mediaNotSupportedException(RequestContext requestContext, String message) {
        log.error("Content type header missing  Exception Occurred");
        writeErrorResponse(requestContext, HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(),
                Error.setErrorResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(),
                        "Content Type header is required"));
    }

    private void roleNotFoundException(RequestContext requestContext, String message) {
        log.error("Role not found Exception Occurred");
        String defaultMsg = "This Resource does not exist at our server.";
        writeErrorResponse(requestContext, HttpStatus.UNAUTHORIZED.value(),
                Error.setErrorResponse(HttpStatus.UNAUTHORIZED.value(),
                        (message == null || message.isEmpty()) ? defaultMsg : message));
    }

    private void alreadyLoggedInException(RequestContext requestContext, String message) {
        log.error("Already have a token. please reauthenticate with username & password.");
        String defaultMsg = "Already have a token. please reauthenticate with username & password.";
        writeErrorResponse(requestContext, HttpStatus.UNAUTHORIZED.value(),
                Error.setErrorResponse(HttpStatus.UNAUTHORIZED.value(),
                        (message == null || message.isEmpty()) ? defaultMsg : message));
    }

    private void writeErrorResponse(RequestContext requestContext, int status, Error response) {
        try {
            HttpServletResponse httpResponse = requestContext.getResponse();
            httpResponse.setContentType(MediaType.APPLICATION_JSON.toString());
            httpResponse.setStatus(status);
            httpResponse.getWriter().append(
                    new GsonBuilder().serializeNulls().create().toJson(response));
            requestContext.setSendZuulResponse(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
