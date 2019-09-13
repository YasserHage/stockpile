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
public class BillCanonical extends Canonical {

	//The bill's primary key.
    private Integer id = null;
    
    //The company that made the bill.
    private CompanyCanonical company = null;
    
    //The user that served the company.
    private UserCanonical user = null;
    
    //Cash, credit or debit
    private String payingMethod = null;
    
    //Checks if the bill is activated, for logic deletion purposes.
    private boolean activated = true;
    
    //The bill's creation date.
    private Date creationDate = null;
    
    //The bill's last update date.
    private Date lastUpdate = null;
}
