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

import com.stockpile.canonicals.TransactionCanonical;
import com.stockpile.canonicals.TransactionCanonicalAsList;
import com.stockpile.domains.Product;
import com.stockpile.domains.Transaction;
import com.stockpile.services.TransactionService;
import com.stockpile.transformations.TransactionTransformation;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

	/**Logger from TransactionController.*/
	private Logger logger = LogManager.getLogger(TransactionController.class);
    
	 /**
     * TransactionService, class meant to run all verbs like CRUD.
     */
    @Autowired
    private TransactionService TransactionService;
    
    /**
     * TransactionTransformation, used as a utility regarding the Transaction's transformation.
     */
    @Autowired
    private TransactionTransformation TransactionTransformation;
    
    //Views a list of available Transactions.
    @GetMapping
    public TransactionCanonicalAsList get() {
        logger.info("Fetching Transactions from database...");
        List<TransactionCanonical> Transactions =
                TransactionTransformation
                        .convert(this.TransactionService
                        .findAll()
                        .stream()
                        .filter(Transaction::isActivated)
                        .collect(Collectors.toList()));
        logger.info("Fetched {} Transactions.", Transactions.size());
        return new TransactionCanonicalAsList(Transactions);
    }
    
    //Views a list of available Transactions on the given page.
    @GetMapping(value = "/pagination")
    public TransactionCanonicalAsList get(
    		Pageable pageable) {
        logger.info("Fetching Transactions from database...");
        // Sorting by creationDate...
        final int PAGE_NUMBER   = pageable.getPageNumber();
        final int PAGE_SIZE     = pageable.getPageSize();
        final String COLUMN     = "creationDate";
        PageRequest pageRequest =  PageRequest.of(PAGE_NUMBER, PAGE_SIZE, Sort.Direction.DESC, COLUMN);
        // Including pageRequest as Pageable
        Page<Transaction> Transaction = this.TransactionService.findAll(pageRequest);
        logger.info("Fetched {} Transactions.", TransactionTransformation.convert(Transaction.getContent()));
        TransactionCanonicalAsList list =
                new TransactionCanonicalAsList(TransactionTransformation.convert(Transaction.getContent()));
        list.setPageNumber(Transaction.getTotalPages());
        list.setElementNumber(Transaction.getTotalElements());
        return list;
    }
    
    //Retriave a Transaction by it's id.
    @GetMapping("/{TransactionId}")
    public ResponseEntity get(@PathVariable Integer TransactionId) {
    	logger.info("Fetching Transaction {} from database...", TransactionId);
    	
    	//Fetching Transaction from database
    	Optional<Transaction> fetchedTransaction = this.TransactionService.findById(TransactionId);
    	
    	if(!fetchedTransaction.isPresent()) {
    		// If there isn't any Transaction fetched from database, 404!
            logger.info("There isn't any Transaction with id {} on database.", TransactionId);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
    	}
    	
    	// If there is a fetched Transaction from database
    	TransactionCanonical TransactionCanonical = TransactionTransformation.convert(fetchedTransaction.get());
        logger.info("Fetched {}.", TransactionCanonical);
        return new ResponseEntity(TransactionCanonical, HttpStatus.OK);
    	
    }
    
    //Create a new Transaction.
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionCanonical post(@RequestBody TransactionCanonical TransactionCanonical) {
    	Transaction transaction = TransactionTransformation.convert(TransactionCanonical);
    	//Fetching product from database
    	//    	Optional<Product> fetchedProduct = this.productService.findById(productId);
    	logger.info("Adding a new Transaction {} to the database", transaction);
        
        // Saving the Transaction!
        return TransactionTransformation.convert(this.TransactionService.save(transaction));	
    }
    
    //Updating an existing Transaction by it's id.
    @PutMapping("/{TransactionId}")
    public TransactionCanonical put(@RequestBody TransactionCanonical TransactionCanonical, @PathVariable Integer TransactionId) {
    	 
    	Transaction Transaction = TransactionTransformation.convert(TransactionCanonical);
    	logger.info("Updating the Transaction {} on database...", Transaction);
         // Fetching Transaction from database layer...
         Optional<Transaction> fetchedTransaction = this.TransactionService.findById(TransactionId);
         
      // Checking if there is a Transaction with the id passed by parameter!
         if(fetchedTransaction.isPresent()) {
             // Updating the fetched Transaction's values...
        	 fetchedTransaction.get().setSale(Transaction.getSale());
        	 fetchedTransaction.get().setBill(Transaction.getBill());
        	 fetchedTransaction.get().setProduct(Transaction.getProduct());
        	 fetchedTransaction.get().setTransactionType(Transaction.getTransactionType());
        	 fetchedTransaction.get().setQuantity(Transaction.getQuantity());
        	 fetchedTransaction.get().setPrice(Transaction.getPrice());
             
             // Updating the Transaction!
        	 return TransactionTransformation.convert(TransactionService.save(fetchedTransaction.get()));    	
         } else {
        	 return new TransactionCanonical();
         }
    }
    
    //Deletes a Transaction by it's id.
    @DeleteMapping("/{TransactionId}")
    public TransactionCanonical delete(@PathVariable Integer TransactionId) {
        logger.info("Deleting the Transaction with id {} from the database...", TransactionId);
        return TransactionTransformation.convert(TransactionService.deleteById(TransactionId));
    }
    
}
