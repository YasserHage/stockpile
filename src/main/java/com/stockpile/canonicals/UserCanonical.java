package com.stockpile.canonicals;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor 
@Builder
@Data
@NoArgsConstructor
public class UserCanonical extends Canonical{

	//The user's primary key.
    private Integer id = null;
    
    //The user's name.
    private String name = null;
    
    //Checks if the user is activated, for logic deletion purposes.
    private boolean activated = true;
    
    //The user's creation date.
    private Date creationDate = null;
    
    //The user's last update date.
    private Date lastUpdate = null;
}
