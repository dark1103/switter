package com.artemiy.switter.service;

import com.artemiy.switter.dao.entity.Post;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Artemiy Milaev
 * @since 21.08.2023
 */
public interface PostService {

	List<Post> getPostsByAuthor(String username, Pageable pageable);

	Post createPost(String username, String title, String content);

	List<Post> getFeedForUser(String username);

	Post editPost(String username, Post map);

	void deletePost(Long postId, String username);
}
