package com.artemiy.switter.dao.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Artemiy Milaev
 * @since 21.08.2023
 */
@Entity
@Getter
@Setter
@Table(name = "app_user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String username;
	private String password;
	private String email;

	@OneToMany(mappedBy = "author")
	private Set<Post> posts;

	@OneToMany(mappedBy = "fromUser")
	private Set<UsersRelation> inputRelations;

	@OneToMany(mappedBy = "toUser")
	private Set<UsersRelation> outputRelations;
}