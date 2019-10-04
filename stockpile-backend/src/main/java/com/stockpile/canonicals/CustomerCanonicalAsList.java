package com.stockpile.canonicals;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerCanonicalAsList extends Canonical{



	// List of customers.
	private List<CustomerCanonical> customers;
	
    //The number of pages the customer.
    private Integer pageNumber  = null;
    
    //The number of elements of the customer's table.
    private Long elementNumber  = null;
	
	@JsonCreator
	public CustomerCanonicalAsList(@JsonProperty("customers") List<CustomerCanonical> customers) {
		super();
		this.customers = customers;
	}
}
