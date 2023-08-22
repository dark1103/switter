package com.artemiy.switter.service;

import com.artemiy.switter.dao.entity.Message;
import com.artemiy.switter.dao.entity.User;
import com.artemiy.switter.dao.repository.MessagingRepository;
import com.artemiy.switter.dao.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.artemiy.switter.util.TestHelper.createUser;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Тест реализации сервиса {@link MessagingServiceImpl}
 *
 * @author Artemiy Milaev
 * @since 22.08.2023
 */
@ExtendWith(MockitoExtension.class)
class MessagingServiceImplTest {

	private static final String USERNAME = "testUser";
	private static final String OTHER_USERNAME = "otherUser";
	private static final String CONTENT = "content";

	@InjectMocks
	private MessagingServiceImpl messagingService;
	@Mock
	private MessagingRepository messagingRepository;
	@Mock
	private UserRepository userRepository;

	/**
	 * Отправленное сообщение должно содержать имена отправителя, получателя и содержимое, которые соответствуют
	 * передаваемым значениям
	 */
	@Test
	void sendMessage() {
		when(userRepository.findByUsername(anyString())).thenAnswer(invocation -> {
			User user = new User();
			user.setUsername(invocation.getArgument(0));
			return Optional.of(user);
		});
		when(messagingRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

		Message message = messagingService.sendMessage(USERNAME, OTHER_USERNAME, CONTENT);

		assertThatMessageHasTestValues(message);
	}

	/**
	 * Метод должен единственное сообщение, которое он получит из репозитория с соответствующими отправителем и
	 * получателем
	 */
	@Test
	void getDialog() {
		when(messagingRepository.listMessagesBetween(anyString(), anyString())).thenAnswer(invocation -> {
			Message message = new Message();
			message.setSender(createUser(invocation.getArgument(0)));
			message.setReceiver(createUser(invocation.getArgument(1)));
			message.setContent(CONTENT);
			return Collections.singletonList(message);
		});

		List<Message> messages = messagingService.getDialog(USERNAME, OTHER_USERNAME);

		assertThat(messages).hasSize(1);
		assertThatMessageHasTestValues(messages.get(0));
	}

	private void assertThatMessageHasTestValues(Message message) {
		assertThat(message.getContent()).isEqualTo(CONTENT);
		assertThat(message.getSender().getUsername()).isEqualTo(USERNAME);
		assertThat(message.getReceiver().getUsername()).isEqualTo(OTHER_USERNAME);
	}
}