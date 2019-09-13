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

import com.stockpile.canonicals.ContactCanonical;
import com.stockpile.canonicals.ContactCanonicalAsList;
import com.stockpile.domains.Contact;
import com.stockpile.services.ContactService;
import com.stockpile.transformations.ContactTransformation;

@RestController
@RequestMapping("/contacts")
public class ContactController {

	/**Logger from ContactController.*/
	private Logger logger = LogManager.getLogger(ContactController.class);
	
	 /**
     * ContactService, class meant to run all verbs like CRUD.
     */
    @Autowired
    private ContactService contactService;
    
    /**
     * ContactTransformation, used as a utility regarding the contact's transformation.
     */
    @Autowired
    private ContactTransformation contactTransformation;
    
    //Views a list of available contacts.
    @GetMapping
    public ContactCanonicalAsList get() {
        logger.info("Fetching contacts from database...");
        List<ContactCanonical> contacts =
                contactTransformation
                        .convert(this.contactService
                        .findAll()
                        .stream()
                        .filter(Contact::isActivated)
                        .collect(Collectors.toList()));
        logger.info("Fetched {} contacts.", contacts.size());
        return new ContactCanonicalAsList(contacts);
    }
    
    //Views a list of available contacts on the given page.
    @GetMapping(value = "/pagination")
    public ContactCanonicalAsList get(
    		Pageable pageable) {
        logger.info("Fetching contacts from database...");
        // Sorting by creationDate...
        final int PAGE_NUMBER   = pageable.getPageNumber();
        final int PAGE_SIZE     = pageable.getPageSize();
        final String COLUMN     = "creationDate";
        PageRequest pageRequest =  PageRequest.of(PAGE_NUMBER, PAGE_SIZE, Sort.Direction.DESC, COLUMN);
        // Including pageRequest as Pageable
        Page<Contact> contact = this.contactService.findAll(pageRequest);
        logger.info("Fetched {} contacts.", contactTransformation.convert(contact.getContent()));
        ContactCanonicalAsList list =
                new ContactCanonicalAsList(contactTransformation.convert(contact.getContent()));
        list.setPageNumber(contact.getTotalPages());
        list.setElementNumber(contact.getTotalElements());
        return list;
    }
    
    //Retriave a contact by it's id.
    @GetMapping("/{contactId}")
    public ResponseEntity get(@PathVariable Integer contactId) {
    	logger.info("Fetching contact {} from database...", contactId);
    	
    	//Fetching contact from database
    	Optional<Contact> fetchedContact = this.contactService.findById(contactId);
    	
    	if(!fetchedContact.isPresent()) {
    		// If there isn't any contact fetched from database, 404!
            logger.info("There isn't any contact with id {} on database.", contactId);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
    	}
    	
    	// If there is a fetched contact from database
    	ContactCanonical contactCanonical = contactTransformation.convert(fetchedContact.get());
        logger.info("Fetched {}.", contactCanonical);
        return new ResponseEntity(contactCanonical, HttpStatus.OK);
    	
    }
    
    //Create a new contact.
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContactCanonical post(@RequestBody ContactCanonical contactCanonical) {
    	
    	Contact contact = contactTransformation.convert(contactCanonical);
    	logger.info("Adding a new contact {} to the database", contact);
        
        // Saving the contact!
        return contactTransformation.convert(this.contactService.save(contact));	
    }
    
    //Updating an existing contact by it's id.
    @PutMapping("/{contactId}")
    public ContactCanonical put(@RequestBody ContactCanonical contactCanonical, @PathVariable Integer contactId) {
    	 
    	Contact contact = contactTransformation.convert(contactCanonical);
    	logger.info("Updating the contact {} on database...", contact);
         // Fetching contact from database layer...
         Optional<Contact> fetchedContact = this.contactService.findById(contactId);
         
      // Checking if there is a contact with the id passed by parameter!
         if(fetchedContact.isPresent()) {
             // Updating the fetched contact's values...
        	 fetchedContact.get().setName(contact.getName());
        	 fetchedContact.get().setDescription(contact.getDescription());
        	 fetchedContact.get().setCompany(contact.getCompany());
        	 fetchedContact.get().setType(contact.getType());
        	 fetchedContact.get().setValue(contact.getValue());
        	 
             // Updating the contact!
        	 return contactTransformation.convert(contactService.save(fetchedContact.get()));    	
         } else {
        	 return new ContactCanonical();
         }
    }
    
    //Deletes a contact by it's id.
    @DeleteMapping("/{contactId}")
    public ContactCanonical delete(@PathVariable Integer contactId) {
        logger.info("Deleting the contact with id {} from the database...", contactId);
        return contactTransformation.convert(contactService.deleteById(contactId));
    }
}
