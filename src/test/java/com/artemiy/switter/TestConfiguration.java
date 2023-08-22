package com.artemiy.switter;

import com.artemiy.switter.dao.entity.Post;
import com.artemiy.switter.dao.entity.User;
import com.artemiy.switter.dto.UserDto;
import com.artemiy.switter.dto.post.PostDto;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author Artemiy Milaev
 * @since 22.08.2023
 */
public class TestConfiguration {

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.typeMap(User.class, UserDto.class);
		modelMapper.typeMap(Post.class, PostDto.class);
		return modelMapper;
	}

//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		return http
//			.csrf().disable()
//			.authorizeRequests()
//			.anyRequest()
//			.authenticated()
//			.and()
//			.sessionManagement()
//			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//			.and()
//			.build();
//	}
}
