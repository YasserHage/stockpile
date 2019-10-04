package com.stockpile.canonicals;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCanonicalAsList  extends Canonical{
	
	// List of companies.
		private List<UserCanonical> users;
		
	    //The number of pages the company.
	    private Integer pageNumber  = null;
	    
	    //The number of elements of the company's table.
	    private Long elementNumber  = null;
		
		@JsonCreator
		public UserCanonicalAsList(@JsonProperty("users") List<UserCanonical> users) {
			super();
			this.users = users;
		}

}
