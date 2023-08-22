package com.artemiy.switter.api.v1;

import com.artemiy.switter.dao.entity.Message;
import com.artemiy.switter.dto.messaging.MessageDto;
import com.artemiy.switter.dto.messaging.SendMessageDto;
import com.artemiy.switter.service.MessagingService;
import com.artemiy.switter.util.CollectionUtils;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * Контроллер отвечающий за обмен сообщениями между пользователями
 *
 * @author Artemiy Milaev
 * @since 22.08.2023
 */
@Validated
@RestController
@RequestMapping("/api/v1/messaging")
@RequiredArgsConstructor
public class MessagingController {

	private final MessagingService messagingService;
	private final ModelMapper modelMapper;

	@PostMapping("/send")
	@Operation(summary = "Отправка сообщения пользователю")
	public MessageDto sendMessage(@Valid @RequestBody SendMessageDto sendMessageDto, Principal principal) {
		return mapMessage(
			messagingService.sendMessage(
				principal.getName(),
				sendMessageDto.getReceiver(),
				sendMessageDto.getContent()
			)
		);
	}

	@GetMapping("/dialog/{username}")
	@Operation(summary = "Получение списка всех сообщений с пользователем")
	public List<MessageDto> getDialog(@PathVariable String username, Principal principal) {
		return CollectionUtils.transformList(
			messagingService.getDialog(principal.getName(), username),
			this::mapMessage
		);
	}

	private MessageDto mapMessage(Message message) {
		return modelMapper.map(message, MessageDto.class);
	}
}
