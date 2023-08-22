package com.artemiy.switter.api.v1;

import com.artemiy.switter.dto.UserDto;
import com.artemiy.switter.dto.auth.LoginDto;
import com.artemiy.switter.dto.auth.RegisterDto;
import com.artemiy.switter.service.security.UserRegistrationService;
import com.artemiy.switter.service.security.jwt.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Artemiy Milaev
 * @since 21.08.2023
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

	private final AuthenticationManager authenticationManager;
	private final UserRegistrationService userRegistrationService;
	private final JwtTokenProvider jwtTokenProvider;
	private final ModelMapper modelMapper;

	@PostMapping("/register")
	@Operation(summary = "Регистрация пользователя")
	public UserDto register(@RequestBody RegisterDto registerDto) {
		return modelMapper.map(
			userRegistrationService.register(
				registerDto.getEmail(),
				registerDto.getUsername(),
				registerDto.getPassword()
			),
			UserDto.class
		);
	}

	@PostMapping("/login")
	@Operation(summary = "Вход")
	public String login(@RequestBody LoginDto loginDto) {
		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(
				loginDto.getUsername(),
				loginDto.getPassword()
			)
		);
		return jwtTokenProvider.createToken(authentication.getName());
	}
}
