package com.stockpile.canonicals;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaleCanonicalAsList {

	// List of sales.
	private List<SaleCanonical> sales;
	
    //The number of pages the sale.
    private Integer pageNumber  = null;
    
    //The number of elements of the sale's table.
    private Long elementNumber  = null;
	
	@JsonCreator
	public SaleCanonicalAsList(@JsonProperty("sales") List<SaleCanonical> sales) {
		super();
		this.sales = sales;
	}
	
}
