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
public class Transaction implements Serializable {

	private static final long serialVersionUID = 7234877854882990311L;

	/** The transaction's primary key. */     
    @Id   
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id = null;
    
    /**The sale that this transaction is attached to, if there's.*/
    private Integer sale = null;
    
    /**The bill that this transaction is attached to, if there's.*/
    private Integer bill = null;
    
    /**The product regarding this transaction.*/
    private Integer product = null;
    
    /**Sale or Bill.*/
    private String transactionType = null;
    
    /**The product's quantity.*/
    private Integer quantity = null;
    
    /**The transaction's full price.*/
    private Integer price = null;
    
    /** Checks if the transaction is activated, for logic deletion purposes.*/
    private boolean activated = true;
    
    /** The transaction's creation date. */
    private Date creationDate = null;
    
    /** The transaction's last update date. */
    private Date lastUpdate = null; 
}
