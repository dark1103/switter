package com.artemiy.switter.dto.post;

import lombok.Data;

/**
 * TODO Class Description
 *
 * @author Artemiy Milaev
 * @since 22.08.2023
 */
@Data
public class EditPostDto {
	private long id;
	private String title;
	private String content;
}
