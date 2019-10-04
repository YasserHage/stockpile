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

import com.stockpile.canonicals.LoginCanonical;
import com.stockpile.canonicals.LoginCanonicalAsList;
import com.stockpile.domains.Login;
import com.stockpile.services.LoginService;
import com.stockpile.transformations.LoginTransformation;

@RestController
@RequestMapping("/logins")
public class LoginController {

	/**Logger from LoginController.*/
	private Logger logger = LogManager.getLogger(LoginController.class);
	
	 /**
     * LoginService, class meant to run all verbs like CRUD.
     */
    @Autowired
    private LoginService loginService;
    
    /**
     * LoginTransformation, used as a utility regarding the login's transformation.
     */
    @Autowired
    private LoginTransformation loginTransformation;
    
    //Views a list of available logins.
    @GetMapping
    public LoginCanonicalAsList get() {
        logger.info("Fetching logins from database...");
        List<LoginCanonical> logins =
                loginTransformation
                        .convert(this.loginService
                        .findAll()
                        .stream()
                        .filter(Login::isActivated)
                        .collect(Collectors.toList()));
        logger.info("Fetched {} logins.", logins.size());
        return new LoginCanonicalAsList(logins);
    }
    
    //Views a list of available logins on the given page.
    @GetMapping(value = "/pagination")
    public LoginCanonicalAsList get(
    		Pageable pageable) {
        logger.info("Fetching logins from database...");
        // Sorting by creationDate...
        final int PAGE_NUMBER   = pageable.getPageNumber();
        final int PAGE_SIZE     = pageable.getPageSize();
        final String COLUMN     = "creationDate";
        PageRequest pageRequest =  PageRequest.of(PAGE_NUMBER, PAGE_SIZE, Sort.Direction.DESC, COLUMN);
        // Including pageRequest as Pageable
        Page<Login> login = this.loginService.findAll(pageRequest);
        logger.info("Fetched {} logins.", loginTransformation.convert(login.getContent()));
        LoginCanonicalAsList list =
                new LoginCanonicalAsList(loginTransformation.convert(login.getContent()));
        list.setPageNumber(login.getTotalPages());
        list.setElementNumber(login.getTotalElements());
        return list;
    }
    
    //Retriave a login by it's id.
    @GetMapping("/{loginId}")
    public ResponseEntity get(@PathVariable Integer loginId) {
    	logger.info("Fetching login {} from database...", loginId);
    	
    	//Fetching login from database
    	Optional<Login> fetchedLogin = this.loginService.findById(loginId);
    	
    	if(!fetchedLogin.isPresent()) {
    		// If there isn't any login fetched from database, 404!
            logger.info("There isn't any login with id {} on database.", loginId);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
    	}
    	
    	// If there is a fetched login from database
    	LoginCanonical loginCanonical = loginTransformation.convert(fetchedLogin.get());
        logger.info("Fetched {}.", loginCanonical);
        return new ResponseEntity(loginCanonical, HttpStatus.OK);
    	
    }
    
    //Create a new login.
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LoginCanonical post(@RequestBody LoginCanonical loginCanonical) {
    	
    	Login login = loginTransformation.convert(loginCanonical);
    	logger.info("Adding a new login {} to the database", login);
        
        // Saving the login!
        return loginTransformation.convert(this.loginService.save(login));	
    }
    
    //Updating an existing login by it's id.
    @PutMapping("/{loginId}")
    public LoginCanonical put(@RequestBody LoginCanonical loginCanonical, @PathVariable Integer loginId) {
    	 
    	Login login = loginTransformation.convert(loginCanonical);
    	logger.info("Updating the login {} on database...", login);
         // Fetching login from database layer...
         Optional<Login> fetchedLogin = this.loginService.findById(loginId);
         
      // Checking if there is a login with the id passed by parameter!
         if(fetchedLogin.isPresent()) {
             // Updating the fetched login's values...
        	 fetchedLogin.get().setUserName(login.getUserName());
        	 fetchedLogin.get().setPassword(login.getPassword());
        	 fetchedLogin.get().setUser(login.getUser());
             
             // Updating the login!
        	 return loginTransformation.convert(loginService.save(fetchedLogin.get()));    	
         } else {
        	 return new LoginCanonical();
         }
    }
    
    //Deletes a login by it's id.
    @DeleteMapping("/{loginId}")
    public LoginCanonical delete(@PathVariable Integer loginId) {
        logger.info("Deleting the login with id {} from the database...", loginId);
        return loginTransformation.convert(loginService.deleteById(loginId));
    }
    
}
