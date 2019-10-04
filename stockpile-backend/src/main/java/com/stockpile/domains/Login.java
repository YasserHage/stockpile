package com.stockpile.domains;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
public class Login implements Serializable {

	private static final long serialVersionUID = -2778195863772469880L;

	/** The login's primary key. */     
    @Id   
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id = null;
    
    /**The userName used to sign in.*/
    private String userName = null;
    
    /**The password used to sign in.*/
    private String password = null;
    
    /**The user assigned to this login.*/
    private Integer user = null;
    
    /** Checks if the login is activated, for logic deletion purposes. */
    private boolean activated = true;
    
    /** The login's creation date. */
    private Date creationDate = null;
    
    /** The login's last update date. */
    private Date lastUpdate = null; 
}
