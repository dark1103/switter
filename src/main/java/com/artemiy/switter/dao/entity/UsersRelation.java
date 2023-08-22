package com.artemiy.switter.dao.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Artemiy Milaev
 * @since 21.08.2023
 */
@Entity
@Getter
@Setter
@Table(name = "user_relation")
public class UsersRelation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "from_user", nullable = false)
	private User fromUser;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "to_user", nullable = false)
	private User toUser;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private UserRelationStatus status;
}
