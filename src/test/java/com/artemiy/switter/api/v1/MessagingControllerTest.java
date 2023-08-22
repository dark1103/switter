package com.artemiy.switter.api.v1;

import com.artemiy.switter.TestConfiguration;
import com.artemiy.switter.dao.entity.Message;
import com.artemiy.switter.dao.entity.User;
import com.artemiy.switter.dto.messaging.SendMessageDto;
import com.artemiy.switter.service.MessagingService;
import com.artemiy.switter.util.TestHelper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.Collections;

import static com.artemiy.switter.util.TestHelper.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Тест контроллера {@link MessagingController}
 *
 * @author Artemiy Milaev
 * @since 22.08.2023
 */
@RunWith(SpringRunner.class)
@Import(MessagingController.class)
@WebMvcTest(value = MessagingController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@ContextConfiguration(classes = TestConfiguration.class)
class MessagingControllerTest {
	private static final String CONTENT = "testContent";
	private static final String OTHER_USER = "otherUser";

	@MockBean
	private MessagingService messagingService;
	@Autowired
	private MockMvc mockMvc;

	@Test
	void send() throws Exception {
		SendMessageDto requestBody = new SendMessageDto();
		requestBody.setUsername(OTHER_USER);
		requestBody.setContent(CONTENT);

		Mockito.when(messagingService.sendMessage(anyString(), anyString(), anyString())).thenAnswer(invocation -> {
			Message message = new Message();
			message.setSender(createUser(invocation.getArgument(0)));
			message.setReceiver(createUser(invocation.getArgument(1)));
			message.setContent(invocation.getArgument(2));
			return message;
		});

		RequestBuilder requestBuilder = createPostBuilder("/api/v1/messaging/send", requestBody);

		mockMvc.perform(requestBuilder)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.content").value(CONTENT))
			.andExpect(jsonPath("$.sender.username").value(TestHelper.TEST_USERNAME))
			.andExpect(jsonPath("$.receiver.username").value(OTHER_USER));
	}

	@Test
	void dialog() throws Exception {

		Mockito.when(messagingService.getDialog(anyString(), anyString())).thenAnswer(invocation -> {
			Message message = new Message();
			message.setSender(createUser(invocation.getArgument(0)));
			message.setReceiver(createUser(invocation.getArgument(1)));
			message.setContent(CONTENT);
			return Collections.singletonList(message);
		});

		RequestBuilder requestBuilder = createGetBuilder("/api/v1/messaging/dialog/" + OTHER_USER);
		mockMvc.perform(requestBuilder)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].content").value(CONTENT))
			.andExpect(jsonPath("$[0].sender.username").value(TEST_USERNAME))
			.andExpect(jsonPath("$[0].receiver.username").value(OTHER_USER));
	}

}