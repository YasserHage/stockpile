package com.stockpile.utils;

import org.springframework.stereotype.Service;

import com.stockpile.canonicals.UserCanonical;
import com.stockpile.domains.User;

@Service
public class UserBeanUtil {

	/**
	 * The toUserCanonical(User) method will transform a User into a UserCanonical.
	 * 
	 * @param user			- The User to be transformed.
	 * @return UserCanonical - The transformed UserCanonical.
	 */
	public UserCanonical toUserCanonical(User user) {
		return UserCanonical
				.builder()
				.id(user.getId())
				.name(user.getName())
				.activated(user.isActivated())
				.creationDate(user.getCreationDate())
				.lastUpdate(user.getLastUpdate())
				.build();
	}
	
	/**
	 * The toUser(UserCanonical) method will transform a UserCanonical into a User.
	 * 
	 * @param user			- The UserCanonical to be transformed.
	 * @return User			- The transformed User.
	 */
	public User toUser(UserCanonical user) {
		return User
				.builder()
				.id(user.getId())
				.name(user.getName())
				.activated(user.isActivated())
				.creationDate(user.getCreationDate())
				.lastUpdate(user.getLastUpdate())
				.build();
	}
	
}
