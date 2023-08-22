package com.artemiy.switter.service;

import com.artemiy.switter.dao.entity.Post;
import com.artemiy.switter.dao.repository.PostRepository;
import com.artemiy.switter.dao.repository.UserRepository;
import com.artemiy.switter.exception.UnauthorizedAccessException;
import com.artemiy.switter.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * @author Artemiy Milaev
 * @since 21.08.2023
 */
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

	private final PostRepository postRepository;
	private final UserRepository userRepository;

	@Override
	public List<Post> getPostsByAuthor(String username, Pageable pageable) {
		return postRepository.getPostByAuthor_UsernameOrderByCreationDate(username, pageable);
	}

	@Override
	public List<Post> getFeedForUser(String username, Pageable pageable) {
		return postRepository.getPostsForUser(username, pageable);
	}

	@Override
	public Post createPost(String username, String title, String content) {
		Post post = new Post();
		post.setTitle(title);
		post.setContent(content);
		post.setCreationDate(new Date());
		post.setAuthor(
			userRepository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException(username))
		);
		postRepository.save(post);
		return post;
	}

	@Override
	public Post getPost(long postId) {
		return postRepository.findById(postId)
			.orElseThrow(() -> createPostNotFoundException(postId));
	}

	@Override
	public Post editPost(String username, long postId, Post editedPost) {
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> createPostNotFoundException(postId));
		if (!Objects.equals(post.getAuthor().getUsername(), username)) {
			throw new UnauthorizedAccessException(username + " not allowed to edit post with id " + editedPost.getId());
		}
		post.setTitle(editedPost.getTitle());
		post.setContent(editedPost.getContent());
		return postRepository.save(post);
	}

	@Override
	public void deletePost(String username, long postId) {
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> createPostNotFoundException(postId));
		if (!Objects.equals(post.getAuthor().getUsername(), username)) {
			throw new UnauthorizedAccessException(username + " not allowed to delete post " + postId);
		}
		postRepository.deleteById(postId);
	}

	private NoSuchElementException createPostNotFoundException(long postId) {
		return new NoSuchElementException("Post with id " + postId + " not found");
	}
}
