package com.artemiy.switter.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Artemiy Milaev
 * @since 21.08.2023
 */
@Data
public class RegisterDto implements Serializable {
	@NotBlank(message = "Email is required")
	@Email(message = "Invalid email format")
	private String email;
	@NotBlank(message = "Username is required")
	@Size(min = 4, max = 20, message = "Username length must be between 4 and 20 characters")
	private String username;
	@NotBlank(message = "Password is required")
	@Size(min = 8, message = "Password must be at least 8 characters")
	private String password;
}
