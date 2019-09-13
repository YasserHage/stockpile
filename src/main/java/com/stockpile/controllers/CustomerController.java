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

import com.stockpile.canonicals.CustomerCanonical;
import com.stockpile.canonicals.CustomerCanonicalAsList;
import com.stockpile.domains.Customer;
import com.stockpile.services.CustomerService;
import com.stockpile.transformations.CustomerTransformation;

@RestController
@RequestMapping("/customers")
public class CustomerController {

	/**Logger from CustomerController.*/
	private Logger logger = LogManager.getLogger(CustomerController.class);
	
	 /**
     * CustomerService, class meant to run all verbs like CRUD.
     */
    @Autowired
    private CustomerService customerService;
    
    /**
     * CustomerTransformation, used as a utility regarding the customer's transformation.
     */
    @Autowired
    private CustomerTransformation customerTransformation;
    
    //Views a list of available customers.
    @GetMapping
    public CustomerCanonicalAsList get() {
        logger.info("Fetching customers from database...");
        List<CustomerCanonical> customers =
                customerTransformation
                        .convert(this.customerService
                        .findAll()
                        .stream()
                        .filter(Customer::isActivated)
                        .collect(Collectors.toList()));
        logger.info("Fetched {} customers.", customers.size());
        return new CustomerCanonicalAsList(customers);
    }
    
    //Views a list of available customers on the given page.
    @GetMapping(value = "/pagination")
    public CustomerCanonicalAsList get(
    		Pageable pageable) {
        logger.info("Fetching customers from database...");
        // Sorting by creationDate...
        final int PAGE_NUMBER   = pageable.getPageNumber();
        final int PAGE_SIZE     = pageable.getPageSize();
        final String COLUMN     = "creationDate";
        PageRequest pageRequest =  PageRequest.of(PAGE_NUMBER, PAGE_SIZE, Sort.Direction.DESC, COLUMN);
        // Including pageRequest as Pageable
        Page<Customer> customer = this.customerService.findAll(pageRequest);
        logger.info("Fetched {} customers.", customerTransformation.convert(customer.getContent()));
        CustomerCanonicalAsList list =
                new CustomerCanonicalAsList(customerTransformation.convert(customer.getContent()));
        list.setPageNumber(customer.getTotalPages());
        list.setElementNumber(customer.getTotalElements());
        return list;
    }
    
    //Retriave a customer by it's id.
    @GetMapping("/{customerId}")
    public ResponseEntity get(@PathVariable Integer customerId) {
    	logger.info("Fetching customer {} from database...", customerId);
    	
    	//Fetching customer from database
    	Optional<Customer> fetchedCustomer = this.customerService.findById(customerId);
    	
    	if(!fetchedCustomer.isPresent()) {
    		// If there isn't any customer fetched from database, 404!
            logger.info("There isn't any customer with id {} on database.", customerId);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
    	}
    	
    	// If there is a fetched customer from database
    	CustomerCanonical customerCanonical = customerTransformation.convert(fetchedCustomer.get());
        logger.info("Fetched {}.", customerCanonical);
        return new ResponseEntity(customerCanonical, HttpStatus.OK);
    	
    }
    
    //Create a new customer.
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerCanonical post(@RequestBody CustomerCanonical customerCanonical) {
    	
    	Customer customer = customerTransformation.convert(customerCanonical);
    	logger.info("Adding a new customer {} to the database", customer);
        
        // Saving the customer!
        return customerTransformation.convert(this.customerService.save(customer));	
    }
    
    //Updating an existing customer by it's id.
    @PutMapping("/{customerId}")
    public CustomerCanonical put(@RequestBody CustomerCanonical customerCanonical, @PathVariable Integer customerId) {
    	 
    	Customer customer = customerTransformation.convert(customerCanonical);
    	logger.info("Updating the customer {} on database...", customer);
         // Fetching customer from database layer...
         Optional<Customer> fetchedCustomer = this.customerService.findById(customerId);
         
      // Checking if there is a customer with the id passed by parameter!
         if(fetchedCustomer.isPresent()) {
             // Updating the fetched customer's values...
        	 fetchedCustomer.get().setName(customer.getName());
        	 fetchedCustomer.get().setCpf(customer.getCpf());
        	 fetchedCustomer.get().setObservation(customer.getObservation());
             
             // Updating the customer!
        	 return customerTransformation.convert(customerService.save(fetchedCustomer.get()));    	
         } else {
        	 return new CustomerCanonical();
         }
    }
    
    //Deletes a customer by it's id.
    @DeleteMapping("/{customerId}")
    public CustomerCanonical delete(@PathVariable Integer customerId) {
        logger.info("Deleting the customer with id {} from the database...", customerId);
        return customerTransformation.convert(customerService.deleteById(customerId));
    }
    
}
