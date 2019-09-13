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
public class Customer implements Serializable{

	private static final long serialVersionUID = -459046061019102117L;
	
	/** The customer's primary key. */     
    @Id   
    @GeneratedValue
	private Integer id = null;
    
    /** The customer's name. */
    private String name = null;
    
    /** The customer's CPF. */
    private String cpf = null;
    
    /** A observation about the customer. */
    private String observation = null; 
    
    /** Checks if the customer is activated, for logic deletion purposes. */
    private boolean activated = true;
    
    /** The customer's creation date. */
    private Date creationDate = null;
    
    /** The customer's last update date. */
    private Date lastUpdate = null; 

}
