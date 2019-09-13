package com.stockpile.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stockpile.canonicals.ContactCanonical;
import com.stockpile.domains.Contact;
import com.stockpile.transformations.CompanyTransformation;

@Service
public class ContactBeanUtil {
	
	/**CompanyTransformation, used as a utility regarding the company's transformation.*/
    @Autowired
    private CompanyTransformation companyTransformation;
	
	/**
	 * The toContactCanonical(Contact) method will transform a Contact into a ContactCanonical.
	 * 
	 * @param contact			- The Contact to be transformed.
	 * @return ContactCanonical - The transformed ContactCanonical.
	 */
	public ContactCanonical toContactCanonical(Contact contact) {
		return ContactCanonical
				.builder()
				.id(contact.getId())
				.name(contact.getName())
				.description(contact.getDescription())
				.company(companyTransformation.convert(contact.getCompany()))
				.type(contact.getType())
				.value(contact.getValue())
				.activated(contact.isActivated())
				.creationDate(contact.getCreationDate())
				.lastUpdate(contact.getLastUpdate())
				.build();
	}
	
	/**
	 * The toContact(ContactCanonical) method will transform a ContactCanonical into a Contact.
	 * 
	 * @param contact			- The ContactCanonical to be transformed.
	 * @return Contact			- The transformed Contact.
	 */
	public Contact toContact(ContactCanonical contact) {
		return Contact
				.builder()
				.id(contact.getId())
				.name(contact.getName())
				.description(contact.getDescription())
				.company(companyTransformation.convert(contact.getCompany()))
				.type(contact.getType())
				.value(contact.getValue())
				.activated(contact.isActivated())
				.creationDate(contact.getCreationDate())
				.lastUpdate(contact.getLastUpdate())
				.build();
	}
}
