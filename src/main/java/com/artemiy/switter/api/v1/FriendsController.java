package com.artemiy.switter.api.v1;

import com.artemiy.switter.dto.UserDto;
import com.artemiy.switter.service.FriendsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author Artemiy Milaev
 * @since 21.08.2023
 */
@RestController
@RequestMapping("/api/v1/friends")
@RequiredArgsConstructor
public class FriendsController {

	private final FriendsService friendsService;

	@PostMapping("/send_request")
	public void sendRequest(@RequestBody UserDto userDto, Principal principal) {
		friendsService.sendFriendRequest(principal.getName(), userDto.getUsername());
	}

	@PostMapping("/accept_request")
	public void acceptFriendRequest(@RequestBody UserDto userDto, Principal principal) {
		friendsService.acceptFriendRequest(userDto.getUsername(), principal.getName());
	}

	@PostMapping("/reject_request")
	public void rejectFriendRequest(@RequestBody UserDto userDto, Principal principal) {
		friendsService.rejectFriendRequest(userDto.getUsername(), principal.getName());
	}

	@PostMapping("/unfriend")
	public void unfriend(@RequestBody UserDto userDto, Principal principal) {
		friendsService.unfriend(principal.getName(), userDto.getUsername());
	}
}
