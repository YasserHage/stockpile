package com.stockpile.transformations;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stockpile.canonicals.LoginCanonical;
import com.stockpile.domains.Login;
import com.stockpile.utils.LoginBeanUtil;

@Service
public class LoginTransformation {

	/**
	 * LoginBeanUtil class is a kind of utilities regarding the login's bean between Login and LoginCanonical.
	 */
	@Autowired
	private LoginBeanUtil loginBeanUtil;
	
	/**
	 * This convert(Login) method will transform a Login into a LoginCanonical.
	 * 
	 * @param login			- the Login that will be transformed into a LoginCanonical.
	 * @return LoginCanonical - the transformed LoginCanonical.
	 */
	public LoginCanonical convert(Login login) {
		return this.loginBeanUtil.toLoginCanonical(login);
	}
	
	/**
	 * This convert(LoginCanonical) method will transform a LoginCanonical into a Login.
	 * 
	 * @param login			- the LoginCanonical that will be transformed into a Login.
	 * @return Login			- the transformed Login.
	 */
	public Login convert(LoginCanonical login) {
		return this.loginBeanUtil.toLogin(login);
	}
	
	/**
	 * It will transform Collection<Login> into List<LoginCanonical>.
	 * 
	 * @param logins					- the collection that will be transformed into List<LoginCanonical>.
	 * @return List<LoginCanonical>	- the transformed List<LoginCanonical>.
	 */
	public List<LoginCanonical> convert(Collection<Login> logins){
		return logins
				.stream()
				.map(this :: convert)
				.collect(Collectors.toList());
	}
	
}
