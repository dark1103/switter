package com.artemiy.switter.service;

import com.artemiy.switter.dao.entity.UsersRelation;

import java.util.List;

/**
 * @author Artemiy Milaev
 * @since 21.08.2023
 */
public interface FriendsService {
	void sendFriendRequest(String fromUsername, String toUsername);

	void acceptFriendRequest(String fromUsername, String toUsername);

	void rejectFriendRequest(String fromUsername, String toUsername);

	void unfriend(String fromUsername, String toUsername);

	List<UsersRelation> getRelations(String username);
}
