package com.stockpile.domains;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
public class User implements Serializable{

	private static final long serialVersionUID = 2127347710351720684L;

	/** The user's primary key. */     
    @Id   
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id = null;
    
    /** The user's name. */
    private String name = null;
   
    /** Checks if the user is activated, for logic deletion purposes. */
    private boolean activated = true;
    
    /** The user's creation date. */
    private Date creationDate = null;
    
    /** The user's last update date. */
    private Date lastUpdate = null; 
    
}
