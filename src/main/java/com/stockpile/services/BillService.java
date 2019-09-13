package com.stockpile.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.stockpile.domains.Bill;
import com.stockpile.repositories.BillRepository;

@Service
public class BillService {

	/** Meant to run all CRUD like verbs.*/
	@Autowired
	private BillRepository billRepository;
	
	/**
	* The findAll() method will retrieve all bills from database.
	*
	* @return List<Bill> - all bills. 
	*/
	public List<Bill> findAll(){
		// Collection containing all bills
		List<Bill> bills = new ArrayList<>();
		
		// Adding all bills into the list		
        this.billRepository.findAll().forEach(bills :: add);
        
        return bills;	
	}
	
	/**
	* The findAll(Pageable) method will retrieve all bills from database on the current page.
	* 
	* @param pageable   	- the pageable.
	* @return Page<Bill> - all bills on the page. 
	*/
    public Page<Bill> findAll(Pageable pageable) {
        return this.billRepository.findByActivated(true, pageable);
    }
	
	/**
	 * The findById(Integer) method will find a bill by it's id.
	 * 
	 * @param id				- the bill's primary key. 
	 * @return Optional<Movie>  - the bill if there's.
	 */
	
	public Optional<Bill> findById(Integer id){
		return this.billRepository.findById(id);
	}
	
	/**
	 * The save(Bill) method will save a bill into database.
	 * 
	 * @param bill			- the bill to be saved.
	 * @return Bill			- the saved bill.
	 */
	public Bill save(Bill bill) {
		return this.billRepository.save(bill);
	}
	
	/**
	 * The deleteById(Integer) method will logically delete a bill by it's id.
	 * 
	 * @param id			- the bill's primary key.
	 * @return Bill		- the logically deleted bill.
	 */
	public Bill deleteById(Integer id) {
		
		// Bill from database layer, if there's      
		Bill bill = null;
		
		// Fetching from database...
		Optional<Bill> fetchedBill = this.billRepository.findById(id);
		
		if (fetchedBill.isPresent()) {
			
            bill = fetchedBill.get();
            
            // activated as false...
            bill.setActivated(false);
            
            // Updating this bill...
            this.billRepository.save(bill);
		}
		
		return bill;
	}
	
}
