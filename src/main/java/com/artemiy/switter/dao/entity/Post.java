package com.artemiy.switter.dao.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author Artemiy Milaev
 * @since 21.08.2023
 */
@Entity
@Getter
@Setter
@Table(name = "post")
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String title;
	private String content;
	private Date creationDate;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User author;

}
