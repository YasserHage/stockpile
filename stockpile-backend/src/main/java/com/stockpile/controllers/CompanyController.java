package com.stockpile.controllers;

import java.util.Date;
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

import com.stockpile.canonicals.CompanyCanonical;
import com.stockpile.canonicals.CompanyCanonicalAsList;
import com.stockpile.controllers.CompanyController;
import com.stockpile.domains.Company;
import com.stockpile.services.CompanyService;
import com.stockpile.transformations.CompanyTransformation;

@RestController
@RequestMapping("/companies")
public class CompanyController {


	/**Logger from CompanyController.*/
	private Logger logger = LogManager.getLogger(CompanyController.class);
	
	 /**
     * CompanyService, class meant to run all verbs like CRUD.
     */
    @Autowired
    private CompanyService companyService;
    
    /**
     * CompanyTransformation, used as a utility regarding the company's transformation.
     */
    @Autowired
    private CompanyTransformation companyTransformation;
    
    //Views a list of available companies.
    @GetMapping
    public CompanyCanonicalAsList get() {
        logger.info("Fetching companies from database...");
        List<CompanyCanonical> companies =
                companyTransformation
                        .convert(this.companyService
                        .findAll()
                        .stream()
                        .filter(Company::isActivated)
                        .collect(Collectors.toList()));
        logger.info("Fetched {} companies.", companies.size());
        return new CompanyCanonicalAsList(companies);
    }
    
    //Views a list of available companies on the given page.
    @GetMapping(value = "/pagination")
    public CompanyCanonicalAsList get(
    		Pageable pageable) {
        logger.info("Fetching companies from database...");
        // Sorting by creationDate...
        final int PAGE_NUMBER   = pageable.getPageNumber();
        final int PAGE_SIZE     = pageable.getPageSize();
        final String COLUMN     = "creationDate";
        PageRequest pageRequest =  PageRequest.of(PAGE_NUMBER, PAGE_SIZE, Sort.Direction.DESC, COLUMN);
        // Including pageRequest as Pageable
        Page<Company> company = this.companyService.findAll(pageRequest);
        logger.info("Fetched {} companies.", companyTransformation.convert(company.getContent()));
        CompanyCanonicalAsList list =
                new CompanyCanonicalAsList(companyTransformation.convert(company.getContent()));
        list.setPageNumber(company.getTotalPages());
        list.setElementNumber(company.getTotalElements());
        return list;
    }
    
    //Retriave a company by it's id.
    @GetMapping("/{companyId}")
    public ResponseEntity get(@PathVariable Integer companyId) {
    	logger.info("Fetching company {} from database...", companyId);
    	
    	//Fetching company from database
    	Optional<Company> fetchedCompany = this.companyService.findById(companyId);
    	
    	if(!fetchedCompany.isPresent()) {
    		// If there isn't any company fetched from database, 404!
            logger.info("There isn't any company with id {} on database.", companyId);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
    	}
    	
    	// If there is a fetched company from database
    	CompanyCanonical companyCanonical = companyTransformation.convert(fetchedCompany.get());
        logger.info("Fetched {}.", companyCanonical);
        return new ResponseEntity(companyCanonical, HttpStatus.OK);
    	
    }
    
    //Create a new company.
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyCanonical post(@RequestBody CompanyCanonical companyCanonical) {
    	
    	Company company = companyTransformation.convert(companyCanonical);
    	logger.info("Adding a new company {} to the database", company);
        
        // Saving the company!
        return companyTransformation.convert(this.companyService.save(company));	
    }
    
    //Updating an existing company by it's id.
    @PutMapping("/{companyId}")
    public CompanyCanonical put(@RequestBody CompanyCanonical companyCanonical, @PathVariable Integer companyId) {
    	 
    	Company company = companyTransformation.convert(companyCanonical);
    	logger.info("Updating the company {} on database...", company);
         // Fetching company from database layer...
         Optional<Company> fetchedCompany = this.companyService.findById(companyId);
         
      // Checking if there is a company with the id passed by parameter!
         if(fetchedCompany.isPresent()) {
             // Updating the fetched company's values...
             fetchedCompany.get().setDescription(company.getDescription());
             fetchedCompany.get().setName(company.getName());
             
             // Updating the company!
        	 return companyTransformation.convert(companyService.save(fetchedCompany.get()));    	
         } else {
        	 return new CompanyCanonical();
         }
    }
    
    //Deletes a company by it's id.
    @DeleteMapping("/{companyId}")
    public CompanyCanonical delete(@PathVariable Integer companyId) {
        logger.info("Deleting the company with id {} from the database...", companyId);
        return companyTransformation.convert(companyService.deleteById(companyId));
    }
    
}
