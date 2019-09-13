package com.stockpile.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.stockpile.domains.Product;
import com.stockpile.repositories.ProductRepository;

@Service
public class ProductService {

	/** Meant to run all CRUD like verbs.*/
	@Autowired
	private ProductRepository productRepository;
	
	/**
	* The findAll() method will retrieve all products from database.
	*
	* @return List<Product> - all products. 
	*/
	public List<Product> findAll(){
		// Collection containing all products
		List<Product> products = new ArrayList<>();
		
		// Adding all products into the list		
        this.productRepository.findAll().forEach(products :: add);
        
        return products;	
	}
	
	/**
	* The findAll(Pageable) method will retrieve all products from database on the current page.
	* 
	* @param pageable   	- the pageable.
	* @return Page<Product> - all products on the page. 
	*/
    public Page<Product> findAll(Pageable pageable) {
        return this.productRepository.findByActivated(true, pageable);
    }
	
	/**
	 * The findById(Integer) method will find a product by it's id.
	 * 
	 * @param id				- the product's primary key. 
	 * @return Optional<Movie>  - the product if there's.
	 */
	
	public Optional<Product> findById(Integer id){
		return this.productRepository.findById(id);
	}
	
	/**
	 * The save(Product) method will save a product into database.
	 * 
	 * @param product			- the product to be saved.
	 * @return Product			- the saved product.
	 */
	public Product save(Product product) {
		return this.productRepository.save(product);
	}
	
	/**
	 * The deleteById(Integer) method will logically delete a product by it's id.
	 * 
	 * @param id			- the product's primary key.
	 * @return Product		- the logically deleted product.
	 */
	public Product deleteById(Integer id) {
		
		// Product from database layer, if there's      
		Product product = null;
		
		// Fetching from database...
		Optional<Product> fetchedProduct = this.productRepository.findById(id);
		
		if (fetchedProduct.isPresent()) {
			
            product = fetchedProduct.get();
            
            // activated as false...
            product.setActivated(false);
            
            // Updating this product...
            this.productRepository.save(product);
		}
		
		return product;
	}
	
}
