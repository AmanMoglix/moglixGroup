package com.hackathon.auth.controller.advice;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.hackathon.auth.model.Error;
import com.hackathon.auth.utils.exception.AuthenticationException;
import com.hackathon.auth.utils.exception.BadRequestException;
import com.hackathon.auth.utils.exception.DuplicateRecordException;
import com.hackathon.auth.utils.exception.LocationException;
import com.hackathon.auth.utils.exception.NotFoundException;


@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerController.class);

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ResponseBody
    public Error notFoundException(NotFoundException exception) {
        String defaultMsg = "Requested resource not found in our data base. Please try again.";
        logger.error("Exception For : {}", defaultMsg);
        exception.printStackTrace();
        return Error.setErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(), getExceptionMessage(exception.getMessage(), defaultMsg));
    }

    @ExceptionHandler(DuplicateRecordException.class)
    @ResponseStatus(HttpStatus.IM_USED)//208
    @ResponseBody
    public Error duplicateRecordException(DuplicateRecordException exception) {
        String defaultMsg = "Duplicate record can not be allowed.";
        logger.error("Exception For : {}", defaultMsg);
        exception.printStackTrace();
        return Error.setErrorResponse(HttpStatus.IM_USED.value(), getExceptionMessage(exception.getMessage(), defaultMsg));
    }

    @ExceptionHandler(LocationException.class)
    @ResponseStatus(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS)//451
    @ResponseBody
    public Error locationException(LocationException exception) {
        String defaultMsg = "Without location you are not authorised person for this system";
        logger.error("Exception For : {}", defaultMsg);
        exception.printStackTrace();
        return Error.setErrorResponse(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS.value(), getExceptionMessage(exception.getMessage(), defaultMsg));
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)//400
    @ResponseBody
    public Error badRequestException(BadRequestException exception) {
        String defaultMsg = "request received with invalid parameters or May be missing in request payload.";
        logger.error("Exception For : {}", defaultMsg);
        exception.printStackTrace();
        return Error.setErrorResponse(HttpStatus.BAD_REQUEST.value(), getExceptionMessage(exception.getMessage(), defaultMsg));
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)//401
    @ResponseBody
    public Error authenticationException(AuthenticationException exception) {
        String defaultMsg = "Unauthorised access for this resource.";
        logger.error("Exception For : {}", defaultMsg);
        exception.printStackTrace();
        return Error.setErrorResponse(HttpStatus.UNAUTHORIZED.value(), getExceptionMessage(exception.getMessage(), defaultMsg));
    }

    private String getExceptionMessage(String message, String staticMessage) {
        return (message != null && !message.equals("")) ? message : staticMessage;
    }
}
