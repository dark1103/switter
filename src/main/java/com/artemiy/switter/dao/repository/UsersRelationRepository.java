package com.artemiy.switter.dao.repository;

import com.artemiy.switter.dao.entity.UserRelationStatus;
import com.artemiy.switter.dao.entity.UsersRelation;
import org.hibernate.annotations.SQLSelect;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * @author Artemiy Milaev
 * @since 21.08.2023
 */
public interface UsersRelationRepository extends CrudRepository<UsersRelation, Long> {

	@Query("SELECT ur FROM UsersRelation ur " +
		"WHERE ur.fromUser.username = :fromUser AND ur.toUser.username = :toUser")
	Optional<UsersRelation> findUsersRelation(String fromUser, String toUser);

	@Query("SELECT ur.status FROM UsersRelation ur " +
		"WHERE ur.fromUser.username = :fromUser AND ur.toUser.username = :toUser")
	Optional<UserRelationStatus> findUsersRelationStatus(String fromUser, String toUser);

	@Query("UPDATE UsersRelation ur SET ur.status = :status " +
		"WHERE ur.fromUser.username = :fromUser AND ur.toUser.username = :toUser")
	void updateUsersRelationStatus(String fromUser, String toUser, UserRelationStatus status);
}
