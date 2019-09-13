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
public class Bill implements Serializable{

	private static final long serialVersionUID = 8924743608762472771L;

	/** The bill's primary key. */     
    @Id   
    @GeneratedValue
	private Integer id = null;
    
    /**The userName used to sign in.*/
    private User user = null;
    
    /**The password used to sign in.*/
    private Company company = null;
    
    /**Cash, credit or debit.*/
    private String payingMethod = null;
    
    /** Checks if the bill is activated, for logic deletion purposes.*/
    private boolean activated = true;
    
    /** The bill's creation date. */
    private Date creationDate = null;
    
    /** The bill's last update date. */
    private Date lastUpdate = null; 
    
}
