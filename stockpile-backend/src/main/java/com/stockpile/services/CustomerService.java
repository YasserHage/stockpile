package com.stockpile.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.stockpile.domains.Customer;
import com.stockpile.repositories.CustomerRepository;

@Service
public class CustomerService {

	/** Meant to run all CRUD like verbs.*/
	@Autowired
	private CustomerRepository customerRepository;
	
	/**
	* The findAll() method will retrieve all customers from database.
	*
	* @return List<Customer> - all customers. 
	*/
	public List<Customer> findAll(){
		// Collection containing all customers
		List<Customer> customers = new ArrayList<>();
		
		// Adding all customers into the list		
        this.customerRepository.findAll().forEach(customers :: add);
        
        return customers;	
	}
	
	/**
	* The findAll(Pageable) method will retrieve all customers from database on the current page.
	* 
	* @param pageable   	- the pageable.
	* @return Page<Customer> - all customers on the page. 
	*/
    public Page<Customer> findAll(Pageable pageable) {
        return this.customerRepository.findByActivated(true, pageable);
    }
	
	/**
	 * The findById(Integer) method will find a customer by it's id.
	 * 
	 * @param id				- the customer's primary key. 
	 * @return Optional<Movie>  - the customer if there's.
	 */
	
	public Optional<Customer> findById(Integer id){
		return this.customerRepository.findById(id);
	}
	
	/**
	 * The save(Customer) method will save a customer into database.
	 * 
	 * @param customer			- the customer to be saved.
	 * @return Customer			- the saved customer.
	 */
	public Customer save(Customer customer) {
		return this.customerRepository.save(customer);
	}
	
	/**
	 * The deleteById(Integer) method will logically delete a customer by it's id.
	 * 
	 * @param id			- the customer's primary key.
	 * @return Customer		- the logically deleted customer.
	 */
	public Customer deleteById(Integer id) {
		
		// Customer from database layer, if there's      
		Customer customer = null;
		
		// Fetching from database...
		Optional<Customer> fetchedCustomer = this.customerRepository.findById(id);
		
		if (fetchedCustomer.isPresent()) {
			
            customer = fetchedCustomer.get();
            
            // activated as false...
            customer.setActivated(false);
            
            // Updating this customer...
            this.customerRepository.save(customer);
		}
		
		return customer;
	}
	
}
