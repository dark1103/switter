package com.artemiy.switter.dao.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Сообщение
 *
 * @author Artemiy Milaev
 * @since 22.08.2023
 */
@Entity
@Getter
@Setter
@Table(name = "message")
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * Содержимое
	 */
	private String content;
	/**
	 * Дата создания
	 */
	private Date creationDate;

	/**
	 * Отправитель
	 */
	@ManyToOne
	@JoinColumn(name = "sender_id", nullable = false)
	private User sender;

	/**
	 * Получатель
	 */
	@ManyToOne
	@JoinColumn(name = "receiver_id", nullable = false)
	private User receiver;
}
