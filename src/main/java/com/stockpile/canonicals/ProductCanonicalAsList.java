package com.stockpile.canonicals;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCanonicalAsList extends Canonical {

	// List of products.
		private List<ProductCanonical> products;
		
	    //The number of pages the product.
	    private Integer pageNumber  = null;
	    
	    //The number of elements of the product's table.
	    private Long elementNumber  = null;
		
		@JsonCreator
		public ProductCanonicalAsList(@JsonProperty("products") List<ProductCanonical> products) {
			super();
			this.products = products;
		}
		
}
