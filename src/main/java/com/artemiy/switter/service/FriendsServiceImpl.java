package com.artemiy.switter.service;

import com.artemiy.switter.dao.entity.UserRelationStatus;
import com.artemiy.switter.dao.entity.UsersRelation;
import com.artemiy.switter.dao.repository.UserRepository;
import com.artemiy.switter.dao.repository.UsersRelationRepository;
import com.artemiy.switter.exception.UserNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * @author Artemiy Milaev
 * @since 21.08.2023
 */
@Service
@RequiredArgsConstructor
public class FriendsServiceImpl implements FriendsService {
	private final UserRepository userRepository;
	private final UsersRelationRepository usersRelationRepository;

	@Override
	@Transactional
	public void sendFriendRequest(String fromUsername, String toUsername) {
		if(isFriendRequestExist(fromUsername, toUsername)) {
			throw new IllegalArgumentException("Friend request already sent");
		}
		if(isFriendRequestExist(toUsername, fromUsername)) {
			acceptFriendRequest(toUsername, fromUsername);
		} else {
			usersRelationRepository.save(createRelation(fromUsername, toUsername, UserRelationStatus.FRIEND_REQUEST));
		}
	}

	@Override
	@Transactional
	public void acceptFriendRequest(String fromUsername, String toUsername) {
		UsersRelation forwardRelation = getFriendRequest(fromUsername, toUsername);
		forwardRelation.setStatus(UserRelationStatus.FRIEND);
		usersRelationRepository.save(forwardRelation);
		UsersRelation backRelation = usersRelationRepository.findUsersRelation(toUsername, fromUsername)
			.map(usersRelation -> {
				usersRelation.setStatus(UserRelationStatus.FRIEND);
				return usersRelation;
			})
			.orElseGet(() -> createRelation(toUsername, fromUsername, UserRelationStatus.FRIEND));
		usersRelationRepository.save(backRelation);
	}

	private UsersRelation createRelation(String fromUsername, String toUsername, UserRelationStatus status) {
		UsersRelation usersRelation = new UsersRelation();
		usersRelation.setFromUser(
			userRepository.findByUsername(fromUsername)
				.orElseThrow(() -> new UserNotFoundException(fromUsername))
		);
		usersRelation.setToUser(
			userRepository.findByUsername(toUsername)
				.orElseThrow(() -> new UserNotFoundException(toUsername))
		);
		usersRelation.setStatus(status);
		return usersRelation;
	}

	@Override
	public void rejectFriendRequest(String fromUsername, String toUsername) {
		UsersRelation friendRequest = getFriendRequest(fromUsername, toUsername);
		friendRequest.setStatus(UserRelationStatus.SUBSCRIBER);
		usersRelationRepository.save(friendRequest);
	}

	@Override
	@Transactional
	public void unfriend(String fromUsername, String toUsername) {
		UsersRelation forwardRelation =	usersRelationRepository.findUsersRelation(fromUsername, toUsername)
			.orElseThrow(() -> new IllegalArgumentException(fromUsername + " is not friend or subscriber of " + toUsername));
		usersRelationRepository.delete(forwardRelation);
		usersRelationRepository.findUsersRelation(toUsername, fromUsername)
			.filter(backwardRelation -> backwardRelation.getStatus() != UserRelationStatus.SUBSCRIBER)
			.ifPresent(backwardRelation -> {
				backwardRelation.setStatus(UserRelationStatus.SUBSCRIBER);
				usersRelationRepository.save(backwardRelation);
			});
	}

	@Override
	public List<UsersRelation> getRelations(String username) {
		return usersRelationRepository.getUsersRelationByFromUser_Username(username);
	}

	private UsersRelation getFriendRequest(String fromUsername, String toUsername) {
		return findFriendRequest(fromUsername, toUsername)
			.orElseThrow(() -> new NoSuchElementException("Friend request not found"));
	}

	private boolean isFriendRequestExist(String fromUsername, String toUsername) {
		return findFriendRequest(fromUsername, toUsername).isPresent();
	}

	private Optional<UsersRelation> findFriendRequest(String fromUsername, String toUsername) {
		return usersRelationRepository.findUsersRelation(fromUsername, toUsername)
			.filter(usersRelation -> usersRelation.getStatus() == UserRelationStatus.FRIEND_REQUEST);
	}
}
