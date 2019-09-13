package com.stockpile.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.stockpile.domains.User;
import com.stockpile.repositories.UserRepository;

@Service
public class UserService {

	/** Meant to run all CRUD like verbs.*/
	@Autowired
	private UserRepository userRepository;
	
	/**
	* The findAll() method will retrieve all users from database.
	*
	* @return List<User> - all users. 
	*/
	public List<User> findAll(){
		// Collection containing all users
		List<User> users = new ArrayList<>();
		
		// Adding all users into the list		
        this.userRepository.findAll().forEach(users :: add);
        
        return users;	
	}
	
	/**
	* The findAll(Pageable) method will retrieve all users from database on the current page.
	* 
	* @param pageable   	- the pageable.
	* @return Page<User> - all users on the page. 
	*/
    public Page<User> findAll(Pageable pageable) {
        return this.userRepository.findByActivated(true, pageable);
    }
	
	/**
	 * The findById(Integer) method will find a user by it's id.
	 * 
	 * @param id				- the user's primary key. 
	 * @return Optional<Movie>  - the user if there's.
	 */
	
	public Optional<User> findById(Integer id){
		return this.userRepository.findById(id);
	}
	
	/**
	 * The save(User) method will save a user into database.
	 * 
	 * @param user			- the user to be saved.
	 * @return User			- the saved user.
	 */
	public User save(User user) {
		return this.userRepository.save(user);
	}
	
	/**
	 * The deleteById(Integer) method will logically delete a user by it's id.
	 * 
	 * @param id			- the user's primary key.
	 * @return User		- the logically deleted user.
	 */
	public User deleteById(Integer id) {
		
		// User from database layer, if there's      
		User user = null;
		
		// Fetching from database...
		Optional<User> fetchedUser = this.userRepository.findById(id);
		
		if (fetchedUser.isPresent()) {
			
            user = fetchedUser.get();
            
            // activated as false...
            user.setActivated(false);
            
            // Updating this user...
            this.userRepository.save(user);
		}
		
		return user;
	}
	
}
