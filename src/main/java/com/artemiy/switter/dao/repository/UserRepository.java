package com.artemiy.switter.dao.repository;

import com.artemiy.switter.dao.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * @author Artemiy Milaev
 * @since 21.08.2023
 */
public interface UserRepository extends CrudRepository<User, Long> {
	Optional<User> findByUsername(String username);
	Optional<User> findByEmail(String email);
}
