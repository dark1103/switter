package com.artemiy.switter.dao.repository;

import com.artemiy.switter.dao.entity.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Репозиторий сообщений
 *
 * @author Artemiy Milaev
 * @since 22.08.2023
 */
public interface MessagingRepository extends CrudRepository<Message, Long> {

	@Query("SELECT m FROM Message m " +
		"WHERE m.sender.username = :username1 AND m.receiver.username = :username2 OR " +
		"m.sender.username = :username2 AND m.receiver.username = :username1 ORDER BY m.creationDate DESC")
	List<Message> listMessagesBetween(String username1, String username2);

}
