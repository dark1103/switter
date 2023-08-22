package com.artemiy.switter.service.security.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author Artemiy Milaev
 * @since 21.08.2023
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class JwtSecurityConfiguration {

	private final AuthenticationProvider authenticationProvider;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtTokenProvider jwtTokenProvider) throws Exception {
		return http
			.csrf().disable()
			.authorizeRequests()
			.requestMatchers(
				"/api/v1/auth/**",
				"/swagger-resources",
				"/swagger-resources/**",
				"/configuration/ui",
				"/configuration/security",
				"/swagger-ui/**",
				"/v3/api-docs/**",
				"/webjars/**",
				"/swagger-ui.html"
			)
			.permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authenticationProvider(authenticationProvider)
			.addFilterBefore(jwtTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
			.build();
	}

	@Bean
	public JwtTokenFilter jwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
		return new JwtTokenFilter(jwtTokenProvider);
	}
}
