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
public class CustomerCanonical  extends Canonical{


	//The customer's primary key.
    private Integer id = null;
    
    //The customer's name.
    private String name = null;
    
    //The customer's CPF.
    private String cpf = null;
    
    //A observation about the customer.
    private String observation = null;
    
    //Checks if the customer is activated, for logic deletion purposes.
    private boolean activated = true;
    
    //The customer's creation date.
    private Date creationDate = null;
    
    //The customer's last update date.
    private Date lastUpdate = null;
    
}
