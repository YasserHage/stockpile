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
public class ProductCanonical extends Canonical {

	//The product's primary key.
    private Integer id = null;
    
    //The product's name.
    private String name = null;
    
    //The product's description.
    private String description = null;
    
    //The product's quantity in stock.
    private Integer quantity = null;
    
    //The product's price.
    private Double price = null;
    
    //The product's company.
    private CompanyCanonical company = null;
    
    //The url for the product's image.
    private String imageUrl = null;
    
    //Checks if the product is activated, for logic deletion purposes.
    private boolean activated = true;
    
    //The product's creation date.
    private Date creationDate = null;
    
    //The product's last update date.
    private Date lastUpdate = null;
}
