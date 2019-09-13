package com.stockpile.domains;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Sale implements Serializable{

	/** The sale's primary key. */     
    @Id   
    @GeneratedValue
	private Integer id = null;
    
    /**The userName used to sign in.*/
    private User user = null;
    
    /**The password used to sign in.*/
    private Customer customer = null;
    
    /**Cash, credit or debit.*/
    private String payingMethod = null;
    
    /** Checks if the sale is activated, for logic deletion purposes.*/
    private boolean activated = true;
    
    /** The sale's creation date. */
    private Date creationDate = null;
    
    /** The sale's last update date. */
    private Date lastUpdate = null; 
    
}
