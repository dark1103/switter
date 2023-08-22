package com.artemiy.switter.exception;

/**
 * TODO Class Description
 *
 * @author Artemiy Milaev
 * @since 22.08.2023
 */
public class UnauthorizedAccessException extends SecurityException {
	public UnauthorizedAccessException(String message) {
		super(message);
	}
}