package com.stockpile.canonicals;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactCanonicalAsList extends Canonical{

				// List of contacts.
				private List<ContactCanonical> contacts;
				
			    //The number of pages the contact.
			    private Integer pageNumber  = null;
			    
			    //The number of elements of the contact's table.
			    private Long elementNumber  = null;
				
				@JsonCreator
				public ContactCanonicalAsList(@JsonProperty("contacts") List<ContactCanonical> contacts) {
					super();
					this.contacts = contacts;
				}
}
