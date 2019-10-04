package com.stockpile.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.stockpile.domains.Contact;
import com.stockpile.repositories.ContactRepository;

@Service
public class ContactService {

	/** Meant to run all CRUD like verbs.*/
	@Autowired
	private ContactRepository contactRepository;
	
	/**
	* The findAll() method will retrieve all contacts from database.
	*
	* @return List<Contact> - all contacts. 
	*/
	public List<Contact> findAll(){
		// Collection containing all contacts
		List<Contact> contacts = new ArrayList<>();
		
		// Adding all contacts into the list		
        this.contactRepository.findAll().forEach(contacts :: add);
        
        return contacts;	
	}
	
	/**
	* The findAll(Pageable) method will retrieve all contacts from database on the current page.
	* 
	* @param pageable   	- the pageable.
	* @return Page<Contact> - all contacts on the page. 
	*/
    public Page<Contact> findAll(Pageable pageable) {
        return this.contactRepository.findByActivated(true, pageable);
    }
	
	/**
	 * The findById(Integer) method will find a contact by it's id.
	 * 
	 * @param id				- the contact's primary key. 
	 * @return Optional<Movie>  - the contact if there's.
	 */
	
	public Optional<Contact> findById(Integer id){
		return this.contactRepository.findById(id);
	}
	
	/**
	 * The save(Contact) method will save a contact into database.
	 * 
	 * @param contact			- the contact to be saved.
	 * @return Contact			- the saved contact.
	 */
	public Contact save(Contact contact) {
		return this.contactRepository.save(contact);
	}
	
	/**
	 * The deleteById(Integer) method will logically delete a contact by it's id.
	 * 
	 * @param id			- the contact's primary key.
	 * @return Contact		- the logically deleted contact.
	 */
	public Contact deleteById(Integer id) {
		
		// Contact from database layer, if there's      
		Contact contact = null;
		
		// Fetching from database...
		Optional<Contact> fetchedContact = this.contactRepository.findById(id);
		
		if (fetchedContact.isPresent()) {
			
            contact = fetchedContact.get();
            
            // activated as false...
            contact.setActivated(false);
            
            // Updating this contact...
            this.contactRepository.save(contact);
		}
		
		return contact;
	}
}
