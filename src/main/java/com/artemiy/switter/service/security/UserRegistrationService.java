package com.artemiy.switter.service.security;

import com.artemiy.switter.dao.entity.User;

/**
 * @author Artemiy Milaev
 * @since 21.08.2023
 */
public interface UserRegistrationService {

	User register(String mail, String login, String password);

}
