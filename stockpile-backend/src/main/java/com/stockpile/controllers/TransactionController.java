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
    private TransactionService transactionService;
    
    /**
     * TransactionTransformation, used as a utility regarding the Transaction's transformation.
     */
    @Autowired
    private TransactionTransformation transactionTransformation;
    
    //Views a list of available Transactions.
    @GetMapping
    public TransactionCanonicalAsList get() {
        logger.info("Fetching Transactions from database...");
        List<TransactionCanonical> transactions =
                transactionTransformation
                        .convert(this.transactionService
                        .findAll()
                        .stream()
                        .filter(Transaction::isActivated)
                        .collect(Collectors.toList()));
        logger.info("Fetched {} Transactions.", transactions.size());
        return new TransactionCanonicalAsList(transactions);
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
        Page<Transaction> transaction = this.transactionService.findAll(pageRequest);
        logger.info("Fetched {} Transactions.", transactionTransformation.convert(transaction.getContent()));
        TransactionCanonicalAsList list =
                new TransactionCanonicalAsList(transactionTransformation.convert(transaction.getContent()));
        list.setPageNumber(transaction.getTotalPages());
        list.setElementNumber(transaction.getTotalElements());
        return list;
    }
    
    //Retriave a Transaction by it's id.
    @GetMapping("/{transactionId}")
    public ResponseEntity get(@PathVariable Integer transactionId) {
    	logger.info("Fetching Transaction {} from database...", transactionId);
    	
    	//Fetching Transaction from database
    	Optional<Transaction> fetchedTransaction = this.transactionService.findById(transactionId);
    	
    	if(!fetchedTransaction.isPresent()) {
    		// If there isn't any Transaction fetched from database, 404!
            logger.info("There isn't any Transaction with id {} on database.", transactionId);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
    	}
    	
    	// If there is a fetched Transaction from database
    	TransactionCanonical transactionCanonical = transactionTransformation.convert(fetchedTransaction.get());
        logger.info("Fetched {}.", transactionCanonical);
        return new ResponseEntity(transactionCanonical, HttpStatus.OK);
    	
    }
    
    //Create a new Transaction.
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionCanonical post(@RequestBody TransactionCanonical transactionCanonical) {
    	Transaction transaction = transactionTransformation.convert(transactionCanonical);
    	//Fetching product from database
    	//    	Optional<Product> fetchedProduct = this.productService.findById(productId);
    	logger.info("Adding a new Transaction {} to the database", transaction);
        
        // Saving the Transaction!
        return transactionTransformation.convert(this.transactionService.save(transaction));	
    }
    
    //Updating an existing Transaction by it's id.
    @PutMapping("/{transactionId}")
    public TransactionCanonical put(@RequestBody TransactionCanonical transactionCanonical, @PathVariable Integer transactionId) {
    	 
    	Transaction transaction = transactionTransformation.convert(transactionCanonical);
    	logger.info("Updating the Transaction {} on database...", transaction);
         // Fetching Transaction from database layer...
         Optional<Transaction> fetchedTransaction = this.transactionService.findById(transactionId);
         
      // Checking if there is a Transaction with the id passed by parameter!
         if(fetchedTransaction.isPresent()) {
             // Updating the fetched Transaction's values...
        	 fetchedTransaction.get().setSale(transaction.getSale());
        	 fetchedTransaction.get().setBill(transaction.getBill());
        	 fetchedTransaction.get().setProduct(transaction.getProduct());
        	 fetchedTransaction.get().setTransactionType(transaction.getTransactionType());
        	 fetchedTransaction.get().setQuantity(transaction.getQuantity());
        	 fetchedTransaction.get().setPrice(transaction.getPrice());
             
             // Updating the Transaction!
        	 return transactionTransformation.convert(transactionService.save(fetchedTransaction.get()));    	
         } else {
        	 return new TransactionCanonical();
         }
    }
    
    //Deletes a Transaction by it's id.
    @DeleteMapping("/{transactionId}")
    public TransactionCanonical delete(@PathVariable Integer transactionId) {
        logger.info("Deleting the Transaction with id {} from the database...", transactionId);
        return transactionTransformation.convert(transactionService.deleteById(transactionId));
    }
    
}
