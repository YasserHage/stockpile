package com.stockpile.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.stockpile.domains.Transaction;
import com.stockpile.repositories.TransactionRepository;

@Service
public class TransactionService {

	/** Meant to run all CRUD like verbs.*/
	@Autowired
	private TransactionRepository transactionRepository;
	
	/**
	* The findAll() method will retrieve all transactions from database.
	*
	* @return List<Transaction> - all transactions. 
	*/
	public List<Transaction> findAll(){
		// Collection containing all transactions
		List<Transaction> transactions = new ArrayList<>();
		
		// Adding all transactions into the list		
        this.transactionRepository.findAll().forEach(transactions :: add);
        
        return transactions;	
	}
	
	/**
	* The findAll(Pageable) method will retrieve all transactions from database on the current page.
	* 
	* @param pageable   	- the pageable.
	* @return Page<Transaction> - all transactions on the page. 
	*/
    public Page<Transaction> findAll(Pageable pageable) {
        return this.transactionRepository.findByActivated(true, pageable);
    }
	
	/**
	 * The findById(Integer) method will find a transaction by it's id.
	 * 
	 * @param id				- the transaction's primary key. 
	 * @return Optional<Movie>  - the transaction if there's.
	 */
	
	public Optional<Transaction> findById(Integer id){
		return this.transactionRepository.findById(id);
	}
	
	/**
	 * The save(Transaction) method will save a transaction into database.
	 * 
	 * @param transaction			- the transaction to be saved.
	 * @return Transaction			- the saved transaction.
	 */
	public Transaction save(Transaction transaction) {
		return this.transactionRepository.save(transaction);
	}
	
	/**
	 * The deleteById(Integer) method will logically delete a transaction by it's id.
	 * 
	 * @param id			- the transaction's primary key.
	 * @return Transaction		- the logically deleted transaction.
	 */
	public Transaction deleteById(Integer id) {
		
		// Transaction from database layer, if there's      
		Transaction transaction = null;
		
		// Fetching from database...
		Optional<Transaction> fetchedTransaction = this.transactionRepository.findById(id);
		
		if (fetchedTransaction.isPresent()) {
			
            transaction = fetchedTransaction.get();
            
            // activated as false...
            transaction.setActivated(false);
            
            // Updating this transaction...
            this.transactionRepository.save(transaction);
		}
		
		return transaction;
	}
	
}
