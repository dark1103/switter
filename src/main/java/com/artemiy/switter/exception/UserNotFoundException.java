package com.artemiy.switter.exception;

import java.util.NoSuchElementException;

/**
 * TODO Class Description
 *
 * @author Artemiy Milaev
 * @since 22.08.2023
 */
public class UserNotFoundException extends NoSuchElementException {
	public UserNotFoundException(String username) {
		super("User " + username + " not found");
	}
}
