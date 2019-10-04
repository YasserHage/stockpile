package com.stockpile.canonicals;

import java.util.Date;

import com.stockpile.canonicals.TransactionCanonical.TransactionCanonicalBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor 
@Builder
@Data
@NoArgsConstructor
public class TransactionCanonical extends Canonical {

	//The transaction's primary key.
    private Integer id = null;
    
    //The sale that this transaction is attached to, if there's.
    private SaleCanonical sale = null;
    
    //The bill that this transaction is attached to, if there's.
    private BillCanonical bill = null;
    
    //The product regarding this transaction.
    private ProductCanonical product = null;
    
    //Sale or Bill.
    private String transactionType = null;
    
    //The product's quantity.
    private Integer quantity = null;
    
    //The transaction's full price.
    private Integer price = null;  
    
    //Checks if the transaction is activated, for logic deletion purposes.
    private boolean activated = true;
    
    //The transaction's creation date.
    private Date creationDate = null;
    
    //The transaction's last update date.
    private Date lastUpdate = null;
}
