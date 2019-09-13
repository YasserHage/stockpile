package com.stockpile.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.stockpile.canonicals.ProductCanonical;
import com.stockpile.canonicals.ProductCanonicalAsList;
import com.stockpile.domains.Product;
import com.stockpile.services.ProductService;
import com.stockpile.transformations.ProductTransformation;

@RestController
@RequestMapping("/products")
public class ProductController {

	/**Logger from ProductController.*/
	private Logger logger = LogManager.getLogger(ProductController.class);
	
	 /**
     * ProductService, class meant to run all verbs like CRUD.
     */
    @Autowired
    private ProductService productService;
    
    /**
     * ProductTransformation, used as a utility regarding the product's transformation.
     */
    @Autowired
    private ProductTransformation productTransformation;
    
    //Views a list of available products.
    @GetMapping
    public ProductCanonicalAsList get() {
        logger.info("Fetching products from database...");
        List<ProductCanonical> products =
                productTransformation
                        .convert(this.productService
                        .findAll()
                        .stream()
                        .filter(Product::isActivated)
                        .collect(Collectors.toList()));
        logger.info("Fetched {} products.", products.size());
        return new ProductCanonicalAsList(products);
    }
    
    //Views a list of available products on the given page.
    @GetMapping(value = "/pagination")
    public ProductCanonicalAsList get(
    		Pageable pageable) {
        logger.info("Fetching products from database...");
        // Sorting by creationDate...
        final int PAGE_NUMBER   = pageable.getPageNumber();
        final int PAGE_SIZE     = pageable.getPageSize();
        final String COLUMN     = "creationDate";
        PageRequest pageRequest =  PageRequest.of(PAGE_NUMBER, PAGE_SIZE, Sort.Direction.DESC, COLUMN);
        // Including pageRequest as Pageable
        Page<Product> product = this.productService.findAll(pageRequest);
        logger.info("Fetched {} products.", productTransformation.convert(product.getContent()));
        ProductCanonicalAsList list =
                new ProductCanonicalAsList(productTransformation.convert(product.getContent()));
        list.setPageNumber(product.getTotalPages());
        list.setElementNumber(product.getTotalElements());
        return list;
    }
    
    //Retriave a product by it's id.
    @GetMapping("/{productId}")
    public ResponseEntity get(@PathVariable Integer productId) {
    	logger.info("Fetching product {} from database...", productId);
    	
    	//Fetching product from database
    	Optional<Product> fetchedProduct = this.productService.findById(productId);
    	
    	if(!fetchedProduct.isPresent()) {
    		// If there isn't any product fetched from database, 404!
            logger.info("There isn't any product with id {} on database.", productId);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
    	}
    	
    	// If there is a fetched product from database
    	ProductCanonical productCanonical = productTransformation.convert(fetchedProduct.get());
        logger.info("Fetched {}.", productCanonical);
        return new ResponseEntity(productCanonical, HttpStatus.OK);
    	
    }
    
    //Create a new product.
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductCanonical post(@RequestBody ProductCanonical productCanonical) {
    	
    	Product product = productTransformation.convert(productCanonical);
    	logger.info("Adding a new product {} to the database", product);
        
        // Saving the product!
        return productTransformation.convert(this.productService.save(product));	
    }
    
    //Updating an existing product by it's id.
    @PutMapping("/{productId}")
    public ProductCanonical put(@RequestBody ProductCanonical productCanonical, @PathVariable Integer productId) {
    	 
    	Product product = productTransformation.convert(productCanonical);
    	logger.info("Updating the product {} on database...", product);
         // Fetching product from database layer...
         Optional<Product> fetchedProduct = this.productService.findById(productId);
         
      // Checking if there is a product with the id passed by parameter!
         if(fetchedProduct.isPresent()) {
             // Updating the fetched product's values...
        	 fetchedProduct.get().setName(product.getName());
        	 fetchedProduct.get().setDescription(product.getDescription());
        	 fetchedProduct.get().setQuantity(product.getQuantity());
        	 fetchedProduct.get().setPrice(product.getPrice());
        	 fetchedProduct.get().setCompany(product.getCompany());
        	 fetchedProduct.get().setImageUrl(product.getImageUrl());
             
             // Updating the product!
        	 return productTransformation.convert(productService.save(fetchedProduct.get()));    	
         } else {
        	 return new ProductCanonical();
         }
    }
    
    //Deletes a product by it's id.
    @DeleteMapping("/{productId}")
    public ProductCanonical delete(@PathVariable Integer productId) {
        logger.info("Deleting the product with id {} from the database...", productId);
        return productTransformation.convert(productService.deleteById(productId));
    }
    
}
