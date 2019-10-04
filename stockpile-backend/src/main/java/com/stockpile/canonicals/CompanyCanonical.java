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
public class CompanyCanonical extends Canonical{

	//The company's primary key.
    private Integer id = null;
    
    //The company's name.
    private String name = null;
    
    //The company's description.
    private String description = null;
    
    //Checks if the company is activated, for logic deletion purposes.
    private boolean activated = true;
    
    //The company's creation date.
    private Date creationDate = null;
    
    //The company's last update date.
    private Date lastUpdate = null;
    
    
    
    
    
}
