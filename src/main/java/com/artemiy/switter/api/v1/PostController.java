package com.artemiy.switter.api.v1;

import com.artemiy.switter.dao.entity.Post;
import com.artemiy.switter.dto.PageableDto;
import com.artemiy.switter.dto.post.EditPostDto;
import com.artemiy.switter.dto.post.PostDto;
import com.artemiy.switter.service.PostService;
import com.artemiy.switter.util.CollectionUtils;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * @author Artemiy Milaev
 * @since 21.08.2023
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

	@Value("${spring.data.web.pageable.default-page-size}")
	private int defaultPageSize;

	private final PostService postService;
	private final ModelMapper modelMapper;

	@PostMapping("/")
	@Operation(summary = "Создание поста")
	public PostDto createPost(@Valid @RequestBody EditPostDto createPostDto, Principal principal) {
		return mapPost(postService.createPost(principal.getName(), createPostDto.getTitle(), createPostDto.getContent()));
	}

	@GetMapping("/{id}")
	@Operation(summary = "Получение поста по id")
	public PostDto getPost(@PathVariable long id) {
		return mapPost(postService.getPost(id));
	}

	@PutMapping("/{id}")
	@Operation(summary = "Изменение поста")
	public PostDto editPost(@PathVariable long id, @Valid @RequestBody EditPostDto createPostDto, Principal principal) {
		return mapPost(postService.editPost(principal.getName(), id, modelMapper.map(createPostDto, Post.class)));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Удалене поста")
	public void deletePost(@PathVariable long id, Principal principal) {
		postService.deletePost(principal.getName(), id);
	}

	@GetMapping("/feed")
	@Operation(summary = "Получение списка постов для пользователя (собственных и тех на кого подписан)")
	public List<PostDto> getFeed(Principal principal, PageableDto pageableDto) {
		return mapList(postService.getFeedForUser(principal.getName(), createDefaultPageable(pageableDto)));
	}

	@GetMapping("/by_user/{username}")
	@Operation(summary = "Получение списка постов созданных пользователем")
	public List<PostDto> getUserPosts(@PathVariable String username, PageableDto pageableDto) {
		return mapList(postService.getPostsByAuthor(username, createDefaultPageable(pageableDto)));
	}

	private Pageable createDefaultPageable(PageableDto pageableDto) {
		return PageRequest.of(pageableDto.getPage(), defaultPageSize, pageableDto.getDirection(), "creationDate");
	}


	private PostDto mapPost(Post post) {
		return modelMapper.map(post, PostDto.class);
	}

	private List<PostDto> mapList(List<Post> posts) {
		return CollectionUtils.transformList(posts, this::mapPost);
	}
}
