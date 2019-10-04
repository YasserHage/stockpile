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

import com.stockpile.canonicals.BillCanonical;
import com.stockpile.canonicals.BillCanonicalAsList;
import com.stockpile.domains.Bill;
import com.stockpile.services.BillService;
import com.stockpile.transformations.BillTransformation;

@RestController
@RequestMapping("/bills")
public class BillController {

	/**Logger from BillController.*/
	private Logger logger = LogManager.getLogger(BillController.class);
	
	 /**
     * BillService, class meant to run all verbs like CRUD.
     */
    @Autowired
    private BillService billService;
    
    /**
     * BillTransformation, used as a utility regarding the Bill's transformation.
     */
    @Autowired
    private BillTransformation billTransformation;
    
    //Views a list of available Bills.
    @GetMapping
    public BillCanonicalAsList get() {
        logger.info("Fetching Bills from database...");
        List<BillCanonical> bills =
                billTransformation
                        .convert(this.billService
                        .findAll()
                        .stream()
                        .filter(Bill::isActivated)
                        .collect(Collectors.toList()));
        logger.info("Fetched {} Bills.", bills.size());
        return new BillCanonicalAsList(bills);
    }
    
    //Views a list of available Bills on the given page.
    @GetMapping(value = "/pagination")
    public BillCanonicalAsList get(
    		Pageable pageable) {
        logger.info("Fetching Bills from database...");
        // Sorting by creationDate...
        final int PAGE_NUMBER   = pageable.getPageNumber();
        final int PAGE_SIZE     = pageable.getPageSize();
        final String COLUMN     = "creationDate";
        PageRequest pageRequest =  PageRequest.of(PAGE_NUMBER, PAGE_SIZE, Sort.Direction.DESC, COLUMN);
        // Including pageRequest as Pageable
        Page<Bill> bill = this.billService.findAll(pageRequest);
        logger.info("Fetched {} Bills.", billTransformation.convert(bill.getContent()));
        BillCanonicalAsList list =
                new BillCanonicalAsList(billTransformation.convert(bill.getContent()));
        list.setPageNumber(bill.getTotalPages());
        list.setElementNumber(bill.getTotalElements());
        return list;
    }
    
    //Retriave a Bill by it's id.
    @GetMapping("/{billId}")
    public ResponseEntity get(@PathVariable Integer billId) {
    	logger.info("Fetching Bill {} from database...", billId);
    	
    	//Fetching Bill from database
    	Optional<Bill> fetchedBill = this.billService.findById(billId);
    	
    	if(!fetchedBill.isPresent()) {
    		// If there isn't any Bill fetched from database, 404!
            logger.info("There isn't any Bill with id {} on database.", billId);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
    	}
    	
    	// If there is a fetched Bill from database
    	BillCanonical billCanonical = billTransformation.convert(fetchedBill.get());
        logger.info("Fetched {}.", billCanonical);
        return new ResponseEntity(billCanonical, HttpStatus.OK);
    	
    }
    
    //Create a new Bill.
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BillCanonical post(@RequestBody BillCanonical billCanonical) {
    	
    	Bill bill = billTransformation.convert(billCanonical);
    	logger.info("Adding a new Bill {} to the database", bill);
        
        // Saving the Bill!
        return billTransformation.convert(this.billService.save(bill));	
    }
    
    //Updating an existing Bill by it's id.
    @PutMapping("/{billId}")
    public BillCanonical put(@RequestBody BillCanonical billCanonical, @PathVariable Integer billId) {
    	 
    	Bill bill = billTransformation.convert(billCanonical);
    	logger.info("Updating the Bill {} on database...", bill);
         // Fetching Bill from database layer...
         Optional<Bill> fetchedBill = this.billService.findById(billId);
         
      // Checking if there is a Bill with the id passed by parameter!
         if(fetchedBill.isPresent()) {
             // Updating the fetched Bill's values...
        	 fetchedBill.get().setUser(bill.getUser());
        	 fetchedBill.get().setCompany(bill.getCompany());
        	 fetchedBill.get().setPayingMethod(bill.getPayingMethod());
             
             // Updating the Bill!
        	 return billTransformation.convert(billService.save(fetchedBill.get()));    	
         } else {
        	 return new BillCanonical();
         }
    }
    
    //Deletes a Bill by it's id.
    @DeleteMapping("/{billId}")
    public BillCanonical delete(@PathVariable Integer billId) {
        logger.info("Deleting the Bill with id {} from the database...", billId);
        return billTransformation.convert(billService.deleteById(billId));
    }
    
}
