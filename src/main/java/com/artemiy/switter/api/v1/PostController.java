package com.artemiy.switter.api.v1;

import com.artemiy.switter.dao.entity.Post;
import com.artemiy.switter.dto.post.CreatePostDto;
import com.artemiy.switter.dto.PageableDto;
import com.artemiy.switter.dto.post.EditPostDto;
import com.artemiy.switter.dto.post.PostDto;
import com.artemiy.switter.service.PostService;
import com.artemiy.switter.util.CollectionUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * @author Artemiy Milaev
 * @since 21.08.2023
 */
@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

	@Value("${spring.data.web.pageable.default-page-size}")
	private int defaultPageSize;

	private final PostService postService;
	private final ModelMapper modelMapper;

	@GetMapping("/feed")
	public List<PostDto> getFeed(Principal principal) {
		return mapList(postService.getFeedForUser(principal.getName()));
	}

	@GetMapping("/by_user/{username}")
	public List<PostDto> getUserPosts(
		@PathVariable String username,
		PageableDto pageableDto
	) {
		return mapList(postService.getPostsByAuthor(username, createDefaultPageable(pageableDto)));
	}

	private Pageable createDefaultPageable(PageableDto pageableDto) {
		return PageRequest.of(pageableDto.getPage(), defaultPageSize, pageableDto.getDirection(), "creationDate");
	}

	@PostMapping("/create")
	public PostDto createPost(@RequestBody CreatePostDto createPostDto, Principal principal) {
		return mapPost(postService.createPost(principal.getName(), createPostDto.getTitle(), createPostDto.getContent()));
	}

	@PostMapping("/edit")
	public PostDto editPost(@RequestBody EditPostDto createPostDto, Principal principal) {
		return mapPost(postService.editPost(principal.getName(), modelMapper.map(createPostDto, Post.class)));
	}

	@PostMapping("/delete/{id}")
	public void deletePost(@PathVariable Long id, Principal principal) {
		postService.deletePost(id, principal.getName());
	}

	private PostDto mapPost(Post post) {
		return modelMapper.map(post, PostDto.class);
	}

	private List<PostDto> mapList(List<Post> posts) {
		return CollectionUtils.transformList(posts, this::mapPost);
	}
}
