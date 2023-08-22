package com.artemiy.switter.api.v1;

import com.artemiy.switter.dto.RelationDto;
import com.artemiy.switter.dto.UserDto;
import com.artemiy.switter.service.FriendsService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * @author Artemiy Milaev
 * @since 21.08.2023
 */
@Validated
@RestController
@RequestMapping("/api/v1/friends")
@RequiredArgsConstructor
public class FriendsController {

	private final FriendsService friendsService;

	@PostMapping("/send_request")
	@Operation(summary = "Отправка заявки в друзья")
	public void sendRequest(@RequestBody UserDto userDto, Principal principal) {
		friendsService.sendFriendRequest(principal.getName(), userDto.getUsername());
	}

	@PostMapping("/accept_request")
	@Operation(summary = "Принятие заявки в друзья")
	public void acceptFriendRequest(@RequestBody UserDto userDto, Principal principal) {
		friendsService.acceptFriendRequest(userDto.getUsername(), principal.getName());
	}

	@PostMapping("/reject_request")
	@Operation(summary = "Отклонение заявки в друзья")
	public void rejectFriendRequest(@RequestBody UserDto userDto, Principal principal) {
		friendsService.rejectFriendRequest(userDto.getUsername(), principal.getName());
	}

	@PostMapping("/unfriend")
	@Operation(summary = "Удаление из друзей")
	public void unfriend(@RequestBody UserDto userDto, Principal principal) {
		friendsService.unfriend(principal.getName(), userDto.getUsername());
	}

	@GetMapping("/subscriptions")
	@Operation(summary = "Возвращает подписки пользователя")
	public List<RelationDto> getFriendsAndSubscriptions(Principal principal) {
		return friendsService.getRelations(principal.getName()).stream()
			.map(usersRelation -> {
				RelationDto relationDto = new RelationDto();
				relationDto.setUsername(usersRelation.getToUser().getUsername());
				relationDto.setStatus(usersRelation.getStatus().name());
				return relationDto;
			})
			.toList();
	}
}
