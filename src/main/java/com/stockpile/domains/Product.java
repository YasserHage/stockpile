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
public class Product implements Serializable {

	private static final long serialVersionUID = 3108774218478921320L;

	/** The product's primary key. */     
    @Id   
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id = null;
    
    /** The product's name. */
    private String name = null;
    
    /** The product's description. */
    private String description = null; 
    
    /**The product's quantity in stock.*/
    private Integer quantity = null;
    
    /**The product's price.*/
    private Double price = null;
    
    /**The product's company.*/
    private Integer company = null;
    
    /**The url for the product's image.*/
    private String imageUrl = null;
    
    /** Checks if the product is activated, for logic deletion purposes. */
    private boolean activated = true;
    
    /** The product's creation date. */
    private Date creationDate = null;
    
    /** The product's last update date. */
    private Date lastUpdate = null; 
}
