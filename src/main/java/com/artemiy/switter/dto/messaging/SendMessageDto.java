package com.artemiy.switter.dto.messaging;

import lombok.Data;

/**
 * Dto отправки сообщений
 *
 * @author Artemiy Milaev
 * @since 22.08.2023
 */
@Data
public class SendMessageDto {
	private String receiver;
	private String content;
}
