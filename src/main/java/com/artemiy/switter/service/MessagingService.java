package com.artemiy.switter.service;

import com.artemiy.switter.dao.entity.Message;

import java.util.List;

/**
 * Служба обмена сообщениями между пользователями
 *
 * @author Artemiy Milaev
 * @since 22.08.2023
 */
public interface MessagingService {
	/**
	 * Отправляет сообщение
	 *
	 * @param senderUsername имя отправителя
	 * @param receiverUsername имя получателя
	 * @param message сообщение
	 * @return отправленное сообщение
	 */
	Message sendMessage(String senderUsername, String receiverUsername, String message);

	/**
	 * Возвращает список всех сообщений между пользователями
	 *
	 * @param senderUsername имя отправителя
	 * @param receiverUsername имя получателя
	 * @return список сообщений
	 */
	List<Message> getDialog(String senderUsername, String receiverUsername);
}
