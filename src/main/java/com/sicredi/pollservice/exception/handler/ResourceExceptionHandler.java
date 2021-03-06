package com.sicredi.pollservice.exception.handler;

import javax.servlet.http.HttpServletRequest;

import com.sicredi.pollservice.context.logger.Log;
import com.sicredi.pollservice.context.logger.Logger;
import com.sicredi.pollservice.exception.BusinessException;
import com.sicredi.pollservice.exception.ResourceNotFoundException;

import org.springframework.amqp.AmqpException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler {

	@Log
	protected Logger logger;

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorHandler> objectNotFound(ResourceNotFoundException e, HttpServletRequest request) {
		ErrorHandler err = new ErrorHandler(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(),
				"NotFound Exception", e.getMessage(), request.getRequestURI());
		logger.error(err.toString());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ErrorHandler> businessException(BusinessException e, HttpServletRequest request) {
		ErrorHandler err = new ErrorHandler(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				"Business Exception", e.getMessage(), request.getRequestURI());
		logger.error(err.toString());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

	@ExceptionHandler(NumberFormatException.class)
	public ResponseEntity<ErrorHandler> numberFormatException(NumberFormatException e, HttpServletRequest request) {
		ErrorHandler err = new ErrorHandler(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				"NumberFormat Exception", e.getMessage(), request.getRequestURI());
		logger.error(err.toString());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

	@ExceptionHandler(AmqpException.class)
	public ResponseEntity<ErrorHandler> amqpException(AmqpException e, HttpServletRequest request) {
		ErrorHandler err = new ErrorHandler(System.currentTimeMillis(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
				"Amqp Exception", e.getMessage(), request.getRequestURI());
		logger.error(err.toString());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ErrorHandler> dataIntegrity(DataIntegrityViolationException e, HttpServletRequest request) {
		ErrorHandler err = new ErrorHandler(System.currentTimeMillis(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
				"DataIntegrityViolation Exception", e.getMessage(), request.getRequestURI());
		logger.error(err.toString());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
	}
}