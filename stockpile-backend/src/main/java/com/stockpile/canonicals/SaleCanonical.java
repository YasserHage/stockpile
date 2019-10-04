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
public class SaleCanonical extends Canonical {

	//The sale's primary key.
    private Integer id = null;
    
    //The customer that made the sale.
    private CustomerCanonical customer = null;
    
    //The user that served the customer.
    private UserCanonical user = null;
    
    //Cash, credit or debit
    private String payingMethod = null;
    
    //Checks if the sale is activated, for logic deletion purposes.
    private boolean activated = true;
    
    //The sale's creation date.
    private Date creationDate = null;
    
    //The sale's last update date.
    private Date lastUpdate = null;
    
}
