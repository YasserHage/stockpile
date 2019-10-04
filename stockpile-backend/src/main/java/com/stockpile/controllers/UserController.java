package com.stockpile.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.stockpile.canonicals.UserCanonical;
import com.stockpile.canonicals.UserCanonicalAsList;
import com.stockpile.domains.User;
import com.stockpile.services.UserService;
import com.stockpile.transformations.UserTransformation;

@RestController
@RequestMapping("/users")
public class UserController {

	/**Logger from UserController.*/
	private Logger logger = LogManager.getLogger(UserController.class);
	
	 /**
     * UserService, class meant to run all verbs like CRUD.
     */
    @Autowired
    private UserService userService;
    
    /**
     * UserTransformation, used as a utility regarding the user's transformation.
     */
    @Autowired
    private UserTransformation userTransformation;
    
    //Views a list of available users.
    @GetMapping
    public UserCanonicalAsList get() {
        logger.info("Fetching users from database...");
        List<UserCanonical> users =
                userTransformation
                        .convert(this.userService
                        .findAll()
                        .stream()
                        .filter(User::isActivated)
                        .collect(Collectors.toList()));
        logger.info("Fetched {} users.", users.size());
        return new UserCanonicalAsList(users);
    }
    
    //Views a list of available users on the given page.
    @GetMapping(value = "/pagination")
    public UserCanonicalAsList get(
    		Pageable pageable) {
        logger.info("Fetching users from database...");
        // Sorting by creationDate...
        final int PAGE_NUMBER   = pageable.getPageNumber();
        final int PAGE_SIZE     = pageable.getPageSize();
        final String COLUMN     = "creationDate";
        PageRequest pageRequest =  PageRequest.of(PAGE_NUMBER, PAGE_SIZE, Sort.Direction.DESC, COLUMN);
        // Including pageRequest as Pageable
        Page<User> user = this.userService.findAll(pageRequest);
        logger.info("Fetched {} users.", userTransformation.convert(user.getContent()));
        UserCanonicalAsList list =
                new UserCanonicalAsList(userTransformation.convert(user.getContent()));
        list.setPageNumber(user.getTotalPages());
        list.setElementNumber(user.getTotalElements());
        return list;
    }
    
    //Retriave a user by it's id.
    @GetMapping("/{userId}")
    public ResponseEntity get(@PathVariable Integer userId) {
    	logger.info("Fetching user {} from database...", userId);
    	
    	//Fetching user from database
    	Optional<User> fetchedUser = this.userService.findById(userId);
    	
    	if(!fetchedUser.isPresent()) {
    		// If there isn't any user fetched from database, 404!
            logger.info("There isn't any user with id {} on database.", userId);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
    	}
    	
    	// If there is a fetched user from database
    	UserCanonical userCanonical = userTransformation.convert(fetchedUser.get());
        logger.info("Fetched {}.", userCanonical);
        return new ResponseEntity(userCanonical, HttpStatus.OK);
    	
    }
    
    //Create a new user.
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserCanonical post(@RequestBody UserCanonical userCanonical) {
    	
    	User user = userTransformation.convert(userCanonical);
    	logger.info("Adding a new user {} to the database", user);
        
        // Saving the user!
        return userTransformation.convert(this.userService.save(user));	
    }
    
    //Updating an existing user by it's id.
    @PutMapping("/{userId}")
    public UserCanonical put(@RequestBody UserCanonical userCanonical, @PathVariable Integer userId) {
    	 
    	User user = userTransformation.convert(userCanonical);
    	logger.info("Updating the user {} on database...", user);
         // Fetching user from database layer...
         Optional<User> fetchedUser = this.userService.findById(userId);
         
      // Checking if there is a user with the id passed by parameter!
         if(fetchedUser.isPresent()) {
             // Updating the fetched user's values...
        	 fetchedUser.get().setName(user.getName());
             
             // Updating the user!
        	 return userTransformation.convert(userService.save(fetchedUser.get()));    	
         } else {
        	 return new UserCanonical();
         }
    }
    
    //Deletes a user by it's id.
    @DeleteMapping("/{userId}")
    public UserCanonical delete(@PathVariable Integer userId) {
        logger.info("Deleting the user with id {} from the database...", userId);
        return userTransformation.convert(userService.deleteById(userId));
    }
    
}
