package com.stockpile.canonicals;

import java.util.Date;

import com.stockpile.canonicals.LoginCanonical.LoginCanonicalBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor 
@Builder
@Data
@NoArgsConstructor
public class ContactCanonical extends Canonical {

	//The contact's primary key.
    private Integer id = null;
    
    //The contact's name.
    private String name = null;
    
    //The contact's description.
    private String description = null;
  
    //The company using this contact.
    private CompanyCanonical company = null;
    
    //Telephone or Email.
    private String type = null;
    
    //Phone number or email address.
    private String value = null;
    
    //Checks if the contact is activated, for logic deletion purposes.
    private boolean activated = true;
    
    //The contact's creation date.
    private Date creationDate = null;
    
    //The contact's last update date.
    private Date lastUpdate = null;
}
