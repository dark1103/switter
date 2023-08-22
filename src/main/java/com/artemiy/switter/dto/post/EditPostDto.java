package com.artemiy.switter.dto.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author Artemiy Milaev
 * @since 22.08.2023
 */
@Data
public class EditPostDto {
	@NotBlank(message = "Title is required")
	private String title;
	@NotBlank(message = "Content is required")
	@Size(max = 500, message = "Post length must be between less then 500 characters")
	private String content;
}
