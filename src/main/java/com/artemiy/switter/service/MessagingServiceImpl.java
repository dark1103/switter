package com.artemiy.switter.service;

import com.artemiy.switter.dao.entity.Message;
import com.artemiy.switter.dao.repository.MessagingRepository;
import com.artemiy.switter.dao.repository.UserRepository;
import com.artemiy.switter.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author Artemiy Milaev
 * @since 22.08.2023
 */
@Service
@RequiredArgsConstructor
public class MessagingServiceImpl implements MessagingService {

	private final MessagingRepository messagingRepository;
	private final UserRepository userRepository;

	@Override
	public Message sendMessage(String senderUsername, String receiverUsername, String content) {
		Message message = new Message();
		message.setReceiver(
			userRepository.findByUsername(receiverUsername)
				.orElseThrow(() -> new UserNotFoundException(receiverUsername))
		);
		message.setSender(
			userRepository.findByUsername(senderUsername)
				.orElseThrow(() ->  new UserNotFoundException(senderUsername))
		);
		message.setCreationDate(new Date());
		message.setContent(content);
		return messagingRepository.save(message);
	}

	@Override
	public List<Message> getDialog(String senderUsername, String receiverUsername) {
		return messagingRepository.listMessagesBetween(senderUsername, receiverUsername);
	}
}
