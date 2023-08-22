package com.artemiy.switter.dto.post;

import lombok.Data;

/**
 * @author Artemiy Milaev
 * @since 21.08.2023
 */
@Data
public class CreatePostDto {
	private String title;
	private String content;
}
