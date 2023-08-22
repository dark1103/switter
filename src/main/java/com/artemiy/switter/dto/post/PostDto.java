package com.artemiy.switter.dto.post;

import com.artemiy.switter.dao.entity.User;
import com.artemiy.switter.dto.UserDto;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

/**
 * @author Artemiy Milaev
 * @since 21.08.2023
 */
@Data
public class PostDto {
	private Long id;
	private String title;
	private String content;
	private UserDto author;
	private Date creationDate;
}
