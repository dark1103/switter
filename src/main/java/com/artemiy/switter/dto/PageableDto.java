package com.artemiy.switter.dto;

import lombok.Data;
import org.springframework.data.domain.Sort.Direction;

/**
 * TODO Class Description
 *
 * @author Artemiy Milaev
 * @since 22.08.2023
 */
@Data
public class PageableDto {
	private int page = 0;
	private Direction direction = Direction.DESC;
}
