package com.artemiy.switter.service.security;

import com.artemiy.switter.dao.entity.User;
import com.artemiy.switter.dao.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @author Artemiy Milaev
 * @since 21.08.2023
 */
@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(login)
			.orElseThrow(() -> new UsernameNotFoundException("User '" + login + "' not found"));

		return org.springframework.security.core.userdetails.User
			.withUsername(login)
			.password(user.getPassword())
			.accountExpired(false)
			.accountLocked(false)
			.credentialsExpired(false)
			.disabled(false)
			.build();
	}

}
