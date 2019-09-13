package com.stockpile.canonicals;

import java.util.Date;

import com.stockpile.canonicals.ProductCanonical.ProductCanonicalBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor 
@Builder
@Data
@NoArgsConstructor
public class LoginCanonical extends Canonical {

	//The login's primary key.
    private Integer id = null;
    
    //The userName used to sign in.
    private String userName = null;
    
    //The password used to sign in.
    private String password = null;
    
    //The user assigned to this login.
    private UserCanonical user = null;
    
    //Checks if the login is activated, for logic deletion purposes.
    private boolean activated = true;
    
    //The login's creation date.
    private Date creationDate = null;
    
    //The login's last update date.
    private Date lastUpdate = null;
    
}
