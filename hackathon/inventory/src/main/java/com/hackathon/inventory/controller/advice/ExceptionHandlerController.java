package com.hackathon.inventory.controller.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hackathon.inventory.model.Error;
import com.hackathon.inventory.utils.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerController.class);

	@ExceptionHandler(AuthenticationException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED) // 401
	@ResponseBody
	public Error authenticationException(AuthenticationException exception) {
		logger.error("Exception For : Unauthorised access for this resource");
		exception.printStackTrace();
		String defaultMsg = "Unauthorised access for this resource";
		return Error.setErrorResponse(HttpStatus.UNAUTHORIZED.value(),
				getExceptionMessage(exception.getMessage(), defaultMsg));
	}

	@ExceptionHandler(BadRequestException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST) // 400
	@ResponseBody
	public Error badRequestException(BadRequestException exception) {
		logger.error("Exception For : request received with invalid parameters or May be missing in request payload.");
		exception.printStackTrace();
		String defaultMsg = "Bad Request, Request received with invalid parameters or May be missing in request payload.";
		return Error.setErrorResponse(HttpStatus.BAD_REQUEST.value(),
				getExceptionMessage(exception.getMessage(), defaultMsg));
	}

	@ExceptionHandler(DuplicateRecordException.class)
	@ResponseStatus(HttpStatus.IM_USED) // 226
	@ResponseBody
	public Error duplicateRecordException(DuplicateRecordException exception) {
		String defaultMsg = "Duplicate record can not be allowed";
		logger.error("Exception For : {}", defaultMsg);
		exception.printStackTrace();
		return Error.setErrorResponse(HttpStatus.IM_USED.value(),
				getExceptionMessage(exception.getMessage(), defaultMsg));
	}

	@ExceptionHandler(EntityNotPersistException.class)
	@ResponseStatus(HttpStatus.EXPECTATION_FAILED) // 417
	@ResponseBody
	public Error entityException(EntityNotPersistException exception) {
		String defaultMsg = "Entity can not be persisted due to some condition failed.";
		logger.error("Exception For : {}", defaultMsg);
		exception.printStackTrace();
		return Error.setErrorResponse(HttpStatus.EXPECTATION_FAILED.value(),
				getExceptionMessage(exception.getMessage(), defaultMsg));
	}

	@ExceptionHandler(EntityUpdateException.class)
	@ResponseStatus(HttpStatus.EXPECTATION_FAILED) // 417
	@ResponseBody
	public Error entityUpdateException(EntityUpdateException exception) {
		String defaultMsg = "Entity can not be update due to some condition failed.";
		logger.error("Exception For : {}", defaultMsg);
		exception.printStackTrace();
		return Error.setErrorResponse(HttpStatus.EXPECTATION_FAILED.value(),
				getExceptionMessage(exception.getMessage(), defaultMsg));
	}

	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND) // 404
	@ResponseBody
	public Error notFoundException(NotFoundException exception) {
		String defaultMsg = "Requested resource data is not found in our data base. Please try again.";
		logger.error("Exception For : {}", defaultMsg);
		exception.printStackTrace();
		return Error.setErrorResponse(HttpStatus.NOT_FOUND.value(),
				getExceptionMessage(exception.getMessage(), defaultMsg));
	}

	@ExceptionHandler(InvalidPaginationException.class)
	@ResponseStatus(HttpStatus.EXPECTATION_FAILED) // 417
	@ResponseBody
	public Error invalidPaginationException(InvalidPaginationException exception) {
		String defaultMsg = "Invalid pagination offset OR limit. Please try again.";
		logger.error("Exception For : {}", defaultMsg);
		exception.printStackTrace();
		return Error.setErrorResponse(HttpStatus.EXPECTATION_FAILED.value(),
				getExceptionMessage(exception.getMessage(), defaultMsg));
	}

	@ExceptionHandler(InvalidVersionException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST) // 400
	@ResponseBody
	public Error invalidVersionException(InvalidVersionException exception) {
		String defaultMsg = "Invalid update version It can be 0 OR 1. Please try again.";
		logger.error("Exception For : {}", defaultMsg);
		exception.printStackTrace();
		return Error.setErrorResponse(HttpStatus.BAD_REQUEST.value(),
				getExceptionMessage(exception.getMessage(), defaultMsg));
	}

	@ExceptionHandler(InvalidQuantityException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST) // 400
	@ResponseBody
	public Error invalidQuantityException(InvalidQuantityException exception) {
		String defaultMsg = "Invalid Transfer Quantity. Please try again.";
		logger.error("Exception For : {}", defaultMsg);
		exception.printStackTrace();
		return Error.setErrorResponse(HttpStatus.BAD_REQUEST.value(),
				getExceptionMessage(exception.getMessage(), defaultMsg));
	}

	@ExceptionHandler(DiscoveryRequestException.class)
	@ResponseStatus(HttpStatus.EXPECTATION_FAILED) // 417
	@ResponseBody
	public Error discoveryRequestException(DiscoveryRequestException exception) {
		String defaultMsg = "Unexpected result received from other service.";
		logger.error("Exception For : {}", defaultMsg);
		exception.printStackTrace();
		return Error.setErrorResponse(HttpStatus.EXPECTATION_FAILED.value(),
				getExceptionMessage(exception.getMessage(), defaultMsg));
	}

	@ExceptionHandler(DiscoveryServiceException.class)
	@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE) // 503
	@ResponseBody
	public Error discoveryServiceException(DiscoveryServiceException exception) {
		String defaultMsg = "Service Unavailable. Due to unavailability we can not serve this functionality.";
		logger.error("Exception For : {}", defaultMsg);
		exception.printStackTrace();
		return Error.setErrorResponse(HttpStatus.SERVICE_UNAVAILABLE.value(),
				getExceptionMessage(exception.getMessage(), defaultMsg));
	}

	@ExceptionHandler(JsonProcessingException.class)
	@ResponseStatus(HttpStatus.FAILED_DEPENDENCY) // 424
	@ResponseBody
	public Error jsonProcessingException(JsonProcessingException exception) {
		String defaultMsg = "Requested resource output not received due to JSON conversion error. Please try again.";
		logger.error("Exception For : {}", defaultMsg);
		exception.printStackTrace();
		return Error.setErrorResponse(HttpStatus.FAILED_DEPENDENCY.value(),
				getExceptionMessage(exception.getMessage(), defaultMsg));
	}

	private String getExceptionMessage(String message, String staticMessage) {
		return (message != null && !message.equals("")) ? message : staticMessage;
	}
}
