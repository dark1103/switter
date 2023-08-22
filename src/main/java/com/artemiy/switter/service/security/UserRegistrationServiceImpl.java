package com.artemiy.switter.service.security;

import com.artemiy.switter.dao.entity.User;
import com.artemiy.switter.dao.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author Artemiy Milaev
 * @since 21.08.2023
 */
@Service
@RequiredArgsConstructor
public class UserRegistrationServiceImpl implements UserRegistrationService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public User register(String email, String username, String password) {
		if(userRepository.findByUsername(username).isPresent()) {
			throw new IllegalArgumentException("User with username " + username + " already exists");
		}
		if(userRepository.findByEmail(email).isPresent()) {
			throw new IllegalArgumentException("User with email " + email + " already exists");
		}
		User user = new User();
		user.setEmail(email);
		user.setUsername(username);
		user.setPassword(passwordEncoder.encode(password));
		return userRepository.save(user);
	}

}
