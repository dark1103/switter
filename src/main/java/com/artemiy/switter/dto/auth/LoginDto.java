package com.artemiy.switter.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author Artemiy Milaev
 * @since 21.08.2023
 */
@Data
public class LoginDto {
	@NotBlank(message = "Username is required")
	private String username;
	@NotBlank(message = "Password is required")
	private String  password;
}
