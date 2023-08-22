package com.artemiy.switter.dto.messaging;

import com.artemiy.switter.dto.UserDto;
import lombok.Data;

import java.util.Date;

/**
 * Dto уже отправленного сообщения
 *
 * @author Artemiy Milaev
 * @since 22.08.2023
 */
@Data
public class MessageDto {
	private Long id;
	private String content;
	private Date creationDate;
	private UserDto sender;
	private UserDto receiver;
}
