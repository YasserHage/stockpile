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

import com.stockpile.canonicals.SaleCanonical;
import com.stockpile.canonicals.SaleCanonicalAsList;
import com.stockpile.domains.Sale;
import com.stockpile.services.SaleService;
import com.stockpile.transformations.SaleTransformation;

@RestController
@RequestMapping("/Sales")
public class SaleController {

	/**Logger from SaleController.*/
	private Logger logger = LogManager.getLogger(SaleController.class);
	
	 /**
     * SaleService, class meant to run all verbs like CRUD.
     */
    @Autowired
    private SaleService SaleService;
    
    /**
     * SaleTransformation, used as a utility regarding the Sale's transformation.
     */
    @Autowired
    private SaleTransformation SaleTransformation;
    
    //Views a list of available Sales.
    @GetMapping
    public SaleCanonicalAsList get() {
        logger.info("Fetching Sales from database...");
        List<SaleCanonical> Sales =
                SaleTransformation
                        .convert(this.SaleService
                        .findAll()
                        .stream()
                        .filter(Sale::isActivated)
                        .collect(Collectors.toList()));
        logger.info("Fetched {} Sales.", Sales.size());
        return new SaleCanonicalAsList(Sales);
    }
    
    //Views a list of available Sales on the given page.
    @GetMapping(value = "/pagination")
    public SaleCanonicalAsList get(
    		Pageable pageable) {
        logger.info("Fetching Sales from database...");
        // Sorting by creationDate...
        final int PAGE_NUMBER   = pageable.getPageNumber();
        final int PAGE_SIZE     = pageable.getPageSize();
        final String COLUMN     = "creationDate";
        PageRequest pageRequest =  PageRequest.of(PAGE_NUMBER, PAGE_SIZE, Sort.Direction.DESC, COLUMN);
        // Including pageRequest as Pageable
        Page<Sale> Sale = this.SaleService.findAll(pageRequest);
        logger.info("Fetched {} Sales.", SaleTransformation.convert(Sale.getContent()));
        SaleCanonicalAsList list =
                new SaleCanonicalAsList(SaleTransformation.convert(Sale.getContent()));
        list.setPageNumber(Sale.getTotalPages());
        list.setElementNumber(Sale.getTotalElements());
        return list;
    }
    
    //Retriave a Sale by it's id.
    @GetMapping("/{SaleId}")
    public ResponseEntity get(@PathVariable Integer SaleId) {
    	logger.info("Fetching Sale {} from database...", SaleId);
    	
    	//Fetching Sale from database
    	Optional<Sale> fetchedSale = this.SaleService.findById(SaleId);
    	
    	if(!fetchedSale.isPresent()) {
    		// If there isn't any Sale fetched from database, 404!
            logger.info("There isn't any Sale with id {} on database.", SaleId);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
    	}
    	
    	// If there is a fetched Sale from database
    	SaleCanonical SaleCanonical = SaleTransformation.convert(fetchedSale.get());
        logger.info("Fetched {}.", SaleCanonical);
        return new ResponseEntity(SaleCanonical, HttpStatus.OK);
    	
    }
    
    //Create a new Sale.
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SaleCanonical post(@RequestBody SaleCanonical SaleCanonical) {
    	
    	Sale Sale = SaleTransformation.convert(SaleCanonical);
    	logger.info("Adding a new Sale {} to the database", Sale);
        
        // Saving the Sale!
        return SaleTransformation.convert(this.SaleService.save(Sale));	
    }
    
    //Updating an existing Sale by it's id.
    @PutMapping("/{SaleId}")
    public SaleCanonical put(@RequestBody SaleCanonical SaleCanonical, @PathVariable Integer SaleId) {
    	 
    	Sale Sale = SaleTransformation.convert(SaleCanonical);
    	logger.info("Updating the Sale {} on database...", Sale);
         // Fetching Sale from database layer...
         Optional<Sale> fetchedSale = this.SaleService.findById(SaleId);
         
      // Checking if there is a Sale with the id passed by parameter!
         if(fetchedSale.isPresent()) {
             // Updating the fetched Sale's values...
        	 fetchedSale.get().setUser(Sale.getUser());
        	 fetchedSale.get().setCustomer(Sale.getCustomer());
        	 fetchedSale.get().setPayingMethod(Sale.getPayingMethod());
             
             // Updating the Sale!
        	 return SaleTransformation.convert(SaleService.save(fetchedSale.get()));    	
         } else {
        	 return new SaleCanonical();
         }
    }
    
    //Deletes a Sale by it's id.
    @DeleteMapping("/{SaleId}")
    public SaleCanonical delete(@PathVariable Integer SaleId) {
        logger.info("Deleting the Sale with id {} from the database...", SaleId);
        return SaleTransformation.convert(SaleService.deleteById(SaleId));
    }
}
