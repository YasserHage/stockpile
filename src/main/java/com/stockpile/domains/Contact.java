package com.stockpile.domains;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.stockpile.canonicals.CompanyCanonical;
import com.stockpile.domains.Customer.CustomerBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Contact implements Serializable{

	private static final long serialVersionUID = 1157206908980132339L;

	/** The contact's primary key. */     
    @Id   
    @GeneratedValue
	private Integer id = null;
    
    /**The contact's name.*/
    private String name = null;
    
    /**The contact's description.*/
    private String description = null;
  
    /**The company using this contact.*/
    private Company company = null;
    
    /**Telephone or Email.*/
    private String type = null;
    
    /**Phone number or email address.*/
    private String value = null;
    
    /** Checks if the customer is activated, for logic deletion purposes. */
    private boolean activated = true;
    
    /** The customer's creation date. */
    private Date creationDate = null;
    
    /** The customer's last update date. */
    private Date lastUpdate = null; 
    
}
