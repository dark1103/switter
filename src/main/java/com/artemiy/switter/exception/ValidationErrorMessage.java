package com.artemiy.switter.exception;

import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.List;

/**
 * @author Artemiy Milaev
 * @since 22.08.2023
 */
@Getter
public class ValidationErrorMessage extends ErrorMessage {

	private final List<FieldValidationError> errors;

	public ValidationErrorMessage(
		List<FieldValidationError> errors,
		Date timestamp,
		String message,
		String description
	) {
		super(HttpStatus.BAD_REQUEST, timestamp, message, description);
		this.errors = errors;
	}

	@Data
	public static class FieldValidationError {
		private String fieldName;
		private Object rejectedValue;
		private String message;
	}
}
