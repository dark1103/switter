package com.artemiy.switter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.NoSuchElementException;

/**
 * @author Artemiy Milaev
 * @since 22.08.2023
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<ErrorMessage> handleNoSuchElementException(NoSuchElementException e, WebRequest request) {
		return createResponse(HttpStatus.NOT_FOUND, e, request);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorMessage> handleIllegalArgumentException(IllegalArgumentException e, WebRequest request) {
		return createResponse(HttpStatus.BAD_REQUEST, e, request);
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorMessage> handleBadCredentialsException(BadCredentialsException e, WebRequest request) {
		return createResponse(HttpStatus.UNAUTHORIZED, e, request);
	}

	@ExceptionHandler(UnauthorizedAccessException.class)
	public ResponseEntity<ErrorMessage> handleUnauthorizedAccessException(UnauthorizedAccessException e, WebRequest request) {
		return createResponse(HttpStatus.FORBIDDEN, e, request);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMessage> globalExceptionHandler(Exception e, WebRequest request) {
		return createResponse(HttpStatus.INTERNAL_SERVER_ERROR, e, request);
	}

	private ResponseEntity<ErrorMessage> createResponse(HttpStatus status, Exception e, WebRequest request) {
		return new ResponseEntity<>(
			new ErrorMessage(
				status,
				new Date(),
				e.getMessage(),
				request.getDescription(false)
			),
			status
		);
	}
}