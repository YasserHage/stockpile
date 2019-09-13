package com.stockpile.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.stockpile.domains.Company;
import com.stockpile.repositories.CompanyRepository;

@Service
public class CompanyService {

	/** Meant to run all CRUD like verbs.*/
	@Autowired
	private CompanyRepository companyRepository;
	
	/**
	* The findAll() method will retrieve all companies from database.
	*
	* @return List<Company> - all companies. 
	*/
	public List<Company> findAll(){
		// Collection containing all companies
		List<Company> companies = new ArrayList<>();
		
		// Adding all companies into the list		
        this.companyRepository.findAll().forEach(companies :: add);
        
        return companies;	
	}
	
	/**
	* The findAll(Pageable) method will retrieve all companies from database on the current page.
	* 
	* @param pageable   	- the pageable.
	* @return Page<Company> - all companies on the page. 
	*/
    public Page<Company> findAll(Pageable pageable) {
        return this.companyRepository.findByActivated(true, pageable);
    }
	
	/**
	 * The findById(Integer) method will find a company by it's id.
	 * 
	 * @param id				- the company's primary key. 
	 * @return Optional<Movie>  - the company if there's.
	 */
	
	public Optional<Company> findById(Integer id){
		return this.companyRepository.findById(id);
	}
	
	/**
	 * The save(Company) method will save a company into database.
	 * 
	 * @param company			- the company to be saved.
	 * @return Company			- the saved company.
	 */
	public Company save(Company company) {
		return this.companyRepository.save(company);
	}
	
	/**
	 * The deleteById(Integer) method will logically delete a company by it's id.
	 * 
	 * @param id			- the company's primary key.
	 * @return Company		- the logically deleted company.
	 */
	public Company deleteById(Integer id) {
		
		// Company from database layer, if there's      
		Company company = null;
		
		// Fetching from database...
		Optional<Company> fetchedCompany = this.companyRepository.findById(id);
		
		if (fetchedCompany.isPresent()) {
			
            company = fetchedCompany.get();
            
            // activated as false...
            company.setActivated(false);
            
            // Updating this company...
            this.companyRepository.save(company);
		}
		
		return company;
	}
}
