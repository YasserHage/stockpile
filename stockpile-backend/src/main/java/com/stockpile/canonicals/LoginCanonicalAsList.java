package com.stockpile.canonicals;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginCanonicalAsList extends Canonical {

			// List of logins.
			private List<LoginCanonical> logins;
			
		    //The number of pages the login.
		    private Integer pageNumber  = null;
		    
		    //The number of elements of the login's table.
		    private Long elementNumber  = null;
			
			@JsonCreator
			public LoginCanonicalAsList(@JsonProperty("logins") List<LoginCanonical> logins) {
				super();
				this.logins = logins;
			}
}
