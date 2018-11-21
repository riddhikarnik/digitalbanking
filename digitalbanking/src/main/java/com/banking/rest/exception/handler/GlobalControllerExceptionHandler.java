package com.banking.rest.exception.handler;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.banking.rest.constants.ErrorMessages;
import com.banking.rest.response.BaseResponse;
import com.banking.util.exceptions.ValidationException;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

	final static Logger LOG = Logger.getLogger(GlobalControllerExceptionHandler.class);

	@Autowired
	MessageSource messageSource;

	/**
	 * 
	 * Use message codes or default message to throw exception
	 */
	@ExceptionHandler(RuntimeException.class)
	ResponseEntity<BaseResponse> defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {

		LOG.info("Exception thrown by: " + req.getRequestURL());
		LOG.debug("Exception : " + e.getMessage());
		LOG.debug("Locale : " + req.getLocale());

		List<String> errorMessages = new ArrayList<String>();
	
		if (e.getMessage().isEmpty()) {
			errorMessages.add(messageSource.getMessage(ErrorMessages.TECHNICAL_ERROR, null, req.getLocale()));
			return new ResponseEntity<>(new BaseResponse(errorMessages), HttpStatus.SERVICE_UNAVAILABLE);
		} else {
			errorMessages.add(messageSource.getMessage(e.getMessage(), null, e.getMessage(), req.getLocale()));
			return new ResponseEntity<>(new BaseResponse(errorMessages), HttpStatus.SERVICE_UNAVAILABLE);
		}
	}

	/**
	 * 
	 * Use narrowed exception method with an example of NoSuchMessageException
	 */
	@ExceptionHandler(ValidationException.class)
	ResponseEntity<BaseResponse> validationErrorHandler(HttpServletRequest req, Exception e) throws Exception {

		LOG.info("Exception thrown by: " + req.getRequestURL());
		LOG.debug("Exception : " + e.getMessage());
		LOG.debug("Locale : " + req.getLocale());

		List<String> errorMessages = new ArrayList<String>();
		try {
			errorMessages.add(messageSource.getMessage(e.getMessage(), null, req.getLocale()));
			return new ResponseEntity<>(new BaseResponse(errorMessages), HttpStatus.BAD_REQUEST);
		} catch (NoSuchMessageException nsme) {
			errorMessages.add(messageSource.getMessage(ErrorMessages.TECHNICAL_ERROR, null, req.getLocale()));
			return new ResponseEntity<>(new BaseResponse(errorMessages), HttpStatus.SERVICE_UNAVAILABLE);
		}

	}
}
