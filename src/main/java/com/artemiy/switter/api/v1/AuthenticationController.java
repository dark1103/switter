package com.artemiy.switter.api.v1;

import com.artemiy.switter.dto.auth.LoginDto;
import com.artemiy.switter.dto.auth.RegisterDto;
import com.artemiy.switter.service.security.UserRegistrationService;
import com.artemiy.switter.service.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegisterDto registerDto) {
		userRegistrationService.register(
			registerDto.getEmail(),
			registerDto.getUsername(),
			registerDto.getPassword()
		);
		return ResponseEntity.status(HttpStatus.ACCEPTED).build();
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
		authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(
				loginDto.getUsername(),
				loginDto.getPassword()
			)
		);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(jwtTokenProvider.createToken(loginDto.getUsername()));
	}
}
