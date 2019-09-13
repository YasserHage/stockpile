package com.stockpile.canonicals;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyCanonicalAsList extends Canonical{


	// List of companies.
	private List<CompanyCanonical> companies;
	
    //The number of pages the company.
    private Integer pageNumber  = null;
    
    //The number of elements of the company's table.
    private Long elementNumber  = null;
	
	@JsonCreator
	public CompanyCanonicalAsList(@JsonProperty("companies") List<CompanyCanonical> companies) {
		super();
		this.companies = companies;
	}
}
