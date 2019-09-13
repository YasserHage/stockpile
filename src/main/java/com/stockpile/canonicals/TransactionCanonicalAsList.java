package com.stockpile.canonicals;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionCanonicalAsList {

		// List of transactions.
		private List<TransactionCanonical> transactions;
		
	    //The number of pages the transaction.
	    private Integer pageNumber  = null;
	    
	    //The number of elements of the transaction's table.
	    private Long elementNumber  = null;
		
		@JsonCreator
		public TransactionCanonicalAsList(@JsonProperty("transactions") List<TransactionCanonical> transactions) {
			super();
			this.transactions = transactions;
		}
}
