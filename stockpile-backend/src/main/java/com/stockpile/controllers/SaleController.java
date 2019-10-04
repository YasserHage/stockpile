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
@RequestMapping("/sales")
public class SaleController {

	/**Logger from SaleController.*/
	private Logger logger = LogManager.getLogger(SaleController.class);
	
	 /**
     * SaleService, class meant to run all verbs like CRUD.
     */
    @Autowired
    private SaleService saleService;
    
    /**
     * SaleTransformation, used as a utility regarding the Sale's transformation.
     */
    @Autowired
    private SaleTransformation saleTransformation;
    
    //Views a list of available Sales.
    @GetMapping
    public SaleCanonicalAsList get() {
        logger.info("Fetching Sales from database...");
        List<SaleCanonical> sales =
                saleTransformation
                        .convert(this.saleService
                        .findAll()
                        .stream()
                        .filter(Sale::isActivated)
                        .collect(Collectors.toList()));
        logger.info("Fetched {} Sales.", sales.size());
        return new SaleCanonicalAsList(sales);
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
        Page<Sale> sale = this.saleService.findAll(pageRequest);
        logger.info("Fetched {} Sales.", saleTransformation.convert(sale.getContent()));
        SaleCanonicalAsList list =
                new SaleCanonicalAsList(saleTransformation.convert(sale.getContent()));
        list.setPageNumber(sale.getTotalPages());
        list.setElementNumber(sale.getTotalElements());
        return list;
    }
    
    //Retriave a Sale by it's id.
    @GetMapping("/{SaleId}")
    public ResponseEntity get(@PathVariable Integer saleId) {
    	logger.info("Fetching Sale {} from database...", saleId);
    	
    	//Fetching Sale from database
    	Optional<Sale> fetchedSale = this.saleService.findById(saleId);
    	
    	if(!fetchedSale.isPresent()) {
    		// If there isn't any Sale fetched from database, 404!
            logger.info("There isn't any Sale with id {} on database.", saleId);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
    	}
    	
    	// If there is a fetched Sale from database
    	SaleCanonical saleCanonical = saleTransformation.convert(fetchedSale.get());
        logger.info("Fetched {}.", saleCanonical);
        return new ResponseEntity(saleCanonical, HttpStatus.OK);
    	
    }
    
    //Create a new Sale.
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SaleCanonical post(@RequestBody SaleCanonical saleCanonical) {
    	
    	Sale sale = saleTransformation.convert(saleCanonical);
    	logger.info("Adding a new Sale {} to the database", sale);
        
        // Saving the Sale!
        return saleTransformation.convert(this.saleService.save(sale));	
    }
    
    //Updating an existing Sale by it's id.
    @PutMapping("/{saleId}")
    public SaleCanonical put(@RequestBody SaleCanonical saleCanonical, @PathVariable Integer saleId) {
    	 
    	Sale sale = saleTransformation.convert(saleCanonical);
    	logger.info("Updating the Sale {} on database...", sale);
         // Fetching Sale from database layer...
         Optional<Sale> fetchedSale = this.saleService.findById(saleId);
         
      // Checking if there is a Sale with the id passed by parameter!
         if(fetchedSale.isPresent()) {
             // Updating the fetched Sale's values...
        	 fetchedSale.get().setUser(sale.getUser());
        	 fetchedSale.get().setCustomer(sale.getCustomer());
        	 fetchedSale.get().setPayingMethod(sale.getPayingMethod());
             
             // Updating the Sale!
        	 return saleTransformation.convert(saleService.save(fetchedSale.get()));    	
         } else {
        	 return new SaleCanonical();
         }
    }
    
    //Deletes a Sale by it's id.
    @DeleteMapping("/{saleId}")
    public SaleCanonical delete(@PathVariable Integer saleId) {
        logger.info("Deleting the Sale with id {} from the database...", saleId);
        return saleTransformation.convert(saleService.deleteById(saleId));
    }
}
