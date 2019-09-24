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
    private BillService BillService;
    
    /**
     * BillTransformation, used as a utility regarding the Bill's transformation.
     */
    @Autowired
    private BillTransformation BillTransformation;
    
    //Views a list of available Bills.
    @GetMapping
    public BillCanonicalAsList get() {
        logger.info("Fetching Bills from database...");
        List<BillCanonical> Bills =
                BillTransformation
                        .convert(this.BillService
                        .findAll()
                        .stream()
                        .filter(Bill::isActivated)
                        .collect(Collectors.toList()));
        logger.info("Fetched {} Bills.", Bills.size());
        return new BillCanonicalAsList(Bills);
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
        Page<Bill> Bill = this.BillService.findAll(pageRequest);
        logger.info("Fetched {} Bills.", BillTransformation.convert(Bill.getContent()));
        BillCanonicalAsList list =
                new BillCanonicalAsList(BillTransformation.convert(Bill.getContent()));
        list.setPageNumber(Bill.getTotalPages());
        list.setElementNumber(Bill.getTotalElements());
        return list;
    }
    
    //Retriave a Bill by it's id.
    @GetMapping("/{BillId}")
    public ResponseEntity get(@PathVariable Integer BillId) {
    	logger.info("Fetching Bill {} from database...", BillId);
    	
    	//Fetching Bill from database
    	Optional<Bill> fetchedBill = this.BillService.findById(BillId);
    	
    	if(!fetchedBill.isPresent()) {
    		// If there isn't any Bill fetched from database, 404!
            logger.info("There isn't any Bill with id {} on database.", BillId);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
    	}
    	
    	// If there is a fetched Bill from database
    	BillCanonical BillCanonical = BillTransformation.convert(fetchedBill.get());
        logger.info("Fetched {}.", BillCanonical);
        return new ResponseEntity(BillCanonical, HttpStatus.OK);
    	
    }
    
    //Create a new Bill.
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BillCanonical post(@RequestBody BillCanonical BillCanonical) {
    	
    	Bill Bill = BillTransformation.convert(BillCanonical);
    	logger.info("Adding a new Bill {} to the database", Bill);
        
        // Saving the Bill!
        return BillTransformation.convert(this.BillService.save(Bill));	
    }
    
    //Updating an existing Bill by it's id.
    @PutMapping("/{BillId}")
    public BillCanonical put(@RequestBody BillCanonical BillCanonical, @PathVariable Integer BillId) {
    	 
    	Bill Bill = BillTransformation.convert(BillCanonical);
    	logger.info("Updating the Bill {} on database...", Bill);
         // Fetching Bill from database layer...
         Optional<Bill> fetchedBill = this.BillService.findById(BillId);
         
      // Checking if there is a Bill with the id passed by parameter!
         if(fetchedBill.isPresent()) {
             // Updating the fetched Bill's values...
        	 fetchedBill.get().setUser(Bill.getUser());
        	 fetchedBill.get().setCompany(Bill.getCompany());
        	 fetchedBill.get().setPayingMethod(Bill.getPayingMethod());
             
             // Updating the Bill!
        	 return BillTransformation.convert(BillService.save(fetchedBill.get()));    	
         } else {
        	 return new BillCanonical();
         }
    }
    
    //Deletes a Bill by it's id.
    @DeleteMapping("/{BillId}")
    public BillCanonical delete(@PathVariable Integer BillId) {
        logger.info("Deleting the Bill with id {} from the database...", BillId);
        return BillTransformation.convert(BillService.deleteById(BillId));
    }
    
}
