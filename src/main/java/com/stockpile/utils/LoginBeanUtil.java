package com.stockpile.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stockpile.canonicals.LoginCanonical;
import com.stockpile.domains.Login;
import com.stockpile.transformations.UserTransformation;

@Service
public class LoginBeanUtil {

	/**UserTransformation, used as a utility regarding the user's transformation.*/
    @Autowired
    private UserTransformation userTransformation;
	
	/**
	 * The toLoginCanonical(Login) method will transform a Login into a LoginCanonical.
	 * 
	 * @param login			- The Login to be transformed.
	 * @return LoginCanonical - The transformed LoginCanonical.
	 */
	public LoginCanonical toLoginCanonical(Login login) {
		return LoginCanonical
				.builder()
				.id(login.getId())
				.userName(login.getUserName())
				.password(login.getPassword())
				.user(userTransformation.convert(login.getUser()))
				.activated(login.isActivated())
				.creationDate(login.getCreationDate())
				.lastUpdate(login.getLastUpdate())
				.build();
	}
	
	/**
	 * The toLogin(LoginCanonical) method will transform a LoginCanonical into a Login.
	 * 
	 * @param login			- The LoginCanonical to be transformed.
	 * @return Login			- The transformed Login.
	 */
	public Login toLogin(LoginCanonical login) {
		return Login
				.builder()
				.id(login.getId())
				.userName(login.getUserName())
				.password(login.getPassword())
				.user(userTransformation.convert(login.getUser()))
				.activated(login.isActivated())
				.creationDate(login.getCreationDate())
				.lastUpdate(login.getLastUpdate())
				.build();
	}
}
