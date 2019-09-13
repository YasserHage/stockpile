package com.stockpile.domains;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Company implements Serializable{
	
	private static final long serialVersionUID = 8090263914437959876L;

	/** The company's primary key. */     
    @Id   
    @GeneratedValue
	private Integer id = null;
    
    /** The company's name. */
    private String name = null;
    
    /** The company's description. */
    private String description = null; 
    
    /** Checks if the company is activated, for logic deletion purposes. */
    private boolean activated = true;
    
    /** The company's creation date. */
    private Date creationDate = null;
    
    /** The company's last update date. */
    private Date lastUpdate = null; 

}
