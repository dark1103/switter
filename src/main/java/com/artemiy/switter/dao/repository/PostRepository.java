package com.artemiy.switter.dao.repository;

import com.artemiy.switter.dao.entity.Post;
import com.artemiy.switter.dao.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * @author Artemiy Milaev
 * @since 21.08.2023
 */
public interface PostRepository extends CrudRepository<Post, Long> {

	default List<Post> getPostByAuthorOrderByCreationDate(String author, Pageable pageable) {
		return getPostByAuthor_UsernameOrderByCreationDate(author, pageable);
	}

	List<Post> getPostByAuthor_UsernameOrderByCreationDate(String author, Pageable pageable);

	@Query("SELECT p FROM Post p LEFT JOIN UsersRelation r ON p.author.id = r.toUser.id " +
		"WHERE r.fromUser.username = :username OR p.author.username = :username")
	List<Post> getPostsForUser(String username);

}
