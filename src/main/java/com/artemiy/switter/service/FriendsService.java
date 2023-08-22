package com.artemiy.switter.service;

/**
 * @author Artemiy Milaev
 * @since 21.08.2023
 */
public interface FriendsService {
	void sendFriendRequest(String fromUsername, String toUsername);

	void acceptFriendRequest(String fromUsername, String toUsername);

	void rejectFriendRequest(String fromUsername, String toUsername);

	void unfriend(String fromUsername, String toUsername);
}
