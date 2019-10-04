package com.stockpile.transformations;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stockpile.canonicals.ContactCanonical;
import com.stockpile.domains.Contact;
import com.stockpile.utils.ContactBeanUtil;

@Service
public class ContactTransformation {

	/**
	 * ContactBeanUtil class is a kind of utilities regarding the contact's bean between Contact and ContactCanonical.
	 */
	@Autowired
	private ContactBeanUtil contactBeanUtil;
	
	/**
	 * This convert(Contact) method will transform a Contact into a ContactCanonical.
	 * 
	 * @param contact			- the Contact that will be transformed into a ContactCanonical.
	 * @return ContactCanonical - the transformed ContactCanonical.
	 */
	public ContactCanonical convert(Contact contact) {
		return this.contactBeanUtil.toContactCanonical(contact);
	}
	
	/**
	 * This convert(ContactCanonical) method will transform a ContactCanonical into a Contact.
	 * 
	 * @param contact			- the ContactCanonical that will be transformed into a Contact.
	 * @return Contact			- the transformed Contact.
	 */
	public Contact convert(ContactCanonical contact) {
		return this.contactBeanUtil.toContact(contact);
	}
	
	/**
	 * It will transform Collection<Contact> into List<ContactCanonical>.
	 * 
	 * @param contacts					- the collection that will be transformed into List<ContactCanonical>.
	 * @return List<ContactCanonical>	- the transformed List<ContactCanonical>.
	 */
	public List<ContactCanonical> convert(Collection<Contact> contacts){
		return contacts
				.stream()
				.map(this :: convert)
				.collect(Collectors.toList());
	}
}
