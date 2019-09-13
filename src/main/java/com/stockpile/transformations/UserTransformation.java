package com.stockpile.transformations;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stockpile.canonicals.UserCanonical;
import com.stockpile.domains.User;
import com.stockpile.utils.UserBeanUtil;

@Service
public class UserTransformation {

	/**
	 * UserBeanUtil class is a kind of utilities regarding the user's bean between User and UserCanonical.
	 */
	@Autowired
	private UserBeanUtil userBeanUtil;
	
	/**
	 * This convert(User) method will transform a User into a UserCanonical.
	 * 
	 * @param user			- the User that will be transformed into a UserCanonical.
	 * @return UserCanonical - the transformed UserCanonical.
	 */
	public UserCanonical convert(User user) {
		return this.userBeanUtil.toUserCanonical(user);
	}
	
	/**
	 * This convert(UserCanonical) method will transform a UserCanonical into a User.
	 * 
	 * @param user			- the UserCanonical that will be transformed into a User.
	 * @return User			- the transformed User.
	 */
	public User convert(UserCanonical user) {
		return this.userBeanUtil.toUser(user);
	}
	
	/**
	 * It will transform Collection<User> into List<UserCanonical>.
	 * 
	 * @param users					- the collection that will be transformed into List<UserCanonical>.
	 * @return List<UserCanonical>	- the transformed List<UserCanonical>.
	 */
	public List<UserCanonical> convert(Collection<User> users){
		return users
				.stream()
				.map(this :: convert)
				.collect(Collectors.toList());
	}
	
}
