package com.stockpile.canonicals;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BillCanonicalAsList extends Canonical {

	// List of bills.
	private List<BillCanonical> bills;
	
    //The number of pages the bill.
    private Integer pageNumber  = null;
    
    //The number of elements of the bill's table.
    private Long elementNumber  = null;
	
	@JsonCreator
	public BillCanonicalAsList(@JsonProperty("bills") List<BillCanonical> bills) {
		super();
		this.bills = bills;
	}
}
