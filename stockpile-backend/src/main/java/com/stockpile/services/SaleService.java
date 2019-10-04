package com.stockpile.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.stockpile.domains.Sale;
import com.stockpile.repositories.SaleRepository;

@Service
public class SaleService {

	/** Meant to run all CRUD like verbs.*/
	@Autowired
	private SaleRepository saleRepository;
	
	/**
	* The findAll() method will retrieve all sales from database.
	*
	* @return List<Sale> - all sales. 
	*/
	public List<Sale> findAll(){
		// Collection containing all sales
		List<Sale> sales = new ArrayList<>();
		
		// Adding all sales into the list		
        this.saleRepository.findAll().forEach(sales :: add);
        
        return sales;	
	}
	
	/**
	* The findAll(Pageable) method will retrieve all sales from database on the current page.
	* 
	* @param pageable   	- the pageable.
	* @return Page<Sale> - all sales on the page. 
	*/
    public Page<Sale> findAll(Pageable pageable) {
        return this.saleRepository.findByActivated(true, pageable);
    }
	
	/**
	 * The findById(Integer) method will find a sale by it's id.
	 * 
	 * @param id				- the sale's primary key. 
	 * @return Optional<Movie>  - the sale if there's.
	 */
	
	public Optional<Sale> findById(Integer id){
		return this.saleRepository.findById(id);
	}
	
	/**
	 * The save(Sale) method will save a sale into database.
	 * 
	 * @param sale			- the sale to be saved.
	 * @return Sale			- the saved sale.
	 */
	public Sale save(Sale sale) {
		return this.saleRepository.save(sale);
	}
	
	/**
	 * The deleteById(Integer) method will logically delete a sale by it's id.
	 * 
	 * @param id			- the sale's primary key.
	 * @return Sale		- the logically deleted sale.
	 */
	public Sale deleteById(Integer id) {
		
		// Sale from database layer, if there's      
		Sale sale = null;
		
		// Fetching from database...
		Optional<Sale> fetchedSale = this.saleRepository.findById(id);
		
		if (fetchedSale.isPresent()) {
			
            sale = fetchedSale.get();
            
            // activated as false...
            sale.setActivated(false);
            
            // Updating this sale...
            this.saleRepository.save(sale);
		}
		
		return sale;
	}
	
}
