package com.artemiy.switter.dto.messaging;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Dto отправки сообщений
 *
 * @author Artemiy Milaev
 * @since 22.08.2023
 */
@Data
public class SendMessageDto {
	@NotNull(message = "Receiver is required")
	private String receiver;
	@NotBlank(message = "Content is required")
	@Size(max = 500, message = "Message length must be between less then 500 characters")
	private String content;
}
