package com.stockpile.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.stockpile.domains.Login;
import com.stockpile.repositories.LoginRepository;

@Service
public class LoginService {

	/** Meant to run all CRUD like verbs.*/
	@Autowired
	private LoginRepository loginRepository;
	
	/**
	* The findAll() method will retrieve all logins from database.
	*
	* @return List<Login> - all logins. 
	*/
	public List<Login> findAll(){
		// Collection containing all logins
		List<Login> logins = new ArrayList<>();
		
		// Adding all logins into the list		
        this.loginRepository.findAll().forEach(logins :: add);
        
        return logins;	
	}
	
	/**
	* The findAll(Pageable) method will retrieve all logins from database on the current page.
	* 
	* @param pageable   	- the pageable.
	* @return Page<Login> - all logins on the page. 
	*/
    public Page<Login> findAll(Pageable pageable) {
        return this.loginRepository.findByActivated(true, pageable);
    }
	
	/**
	 * The findById(Integer) method will find a login by it's id.
	 * 
	 * @param id				- the login's primary key. 
	 * @return Optional<Movie>  - the login if there's.
	 */
	
	public Optional<Login> findById(Integer id){
		return this.loginRepository.findById(id);
	}
	
	/**
	 * The save(Login) method will save a login into database.
	 * 
	 * @param login			- the login to be saved.
	 * @return Login			- the saved login.
	 */
	public Login save(Login login) {
		return this.loginRepository.save(login);
	}
	
	/**
	 * The deleteById(Integer) method will logically delete a login by it's id.
	 * 
	 * @param id			- the login's primary key.
	 * @return Login		- the logically deleted login.
	 */
	public Login deleteById(Integer id) {
		
		// Login from database layer, if there's      
		Login login = null;
		
		// Fetching from database...
		Optional<Login> fetchedLogin = this.loginRepository.findById(id);
		
		if (fetchedLogin.isPresent()) {
			
            login = fetchedLogin.get();
            
            // activated as false...
            login.setActivated(false);
            
            // Updating this login...
            this.loginRepository.save(login);
		}
		
		return login;
	}
}
