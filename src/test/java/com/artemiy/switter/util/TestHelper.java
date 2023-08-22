package com.artemiy.switter.util;

import com.artemiy.switter.dao.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.security.Principal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * TODO Class Description
 *
 * @author Artemiy Milaev
 * @since 22.08.2023
 */
public class TestHelper {
	public static final String TEST_USERNAME = "testUser";

	public static Principal mockPrincipal() {
		Principal mockPrincipal = Mockito.mock(Principal.class);
		Mockito.when(mockPrincipal.getName()).thenReturn(TEST_USERNAME);
		return mockPrincipal;
	}

	public static MockHttpServletRequestBuilder createGetBuilder(String url) {
		return get(url)
			.principal(mockPrincipal())
			.contentType(MediaType.APPLICATION_JSON);
	}

	public static MockHttpServletRequestBuilder createPostBuilder(String url, Object content) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return createPostBuilder(url)
			.content(mapper.writeValueAsString(content));
	}

	public static MockHttpServletRequestBuilder createPostBuilder(String url) {
		return post(url)
			.principal(mockPrincipal())
			.contentType(MediaType.APPLICATION_JSON);
	}

	public static User createUser(String username) {
		User user = new User();
		user.setUsername(username);
		return user;
	}
}
