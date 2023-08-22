package com.artemiy.switter.exception;

import org.springframework.http.HttpStatus;

import java.util.Date;

/**
 * @author Artemiy Milaev
 * @since 22.08.2023
 */
public class ErrorMessage {
	private final int statusCode;
	private final Date timestamp;
	private final String message;
	private final String description;

	public ErrorMessage(HttpStatus status, Date timestamp, String message, String description) {
		this.statusCode = status.value();
		this.timestamp = timestamp;
		this.message = message;
		this.description = description;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}

	public String getDescription() {
		return description;
	}
}
