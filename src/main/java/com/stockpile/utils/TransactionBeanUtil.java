package com.stockpile.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stockpile.canonicals.TransactionCanonical;
import com.stockpile.domains.Transaction;
import com.stockpile.transformations.BillTransformation;
import com.stockpile.transformations.ProductTransformation;
import com.stockpile.transformations.SaleTransformation;

@Service
public class TransactionBeanUtil {

	/**SaleTransformation, used as a utility regarding the sale's transformation.*/
    @Autowired
    private SaleTransformation saleTransformation;
    
    /**BillTransformation, used as a utility regarding the bill's transformation.*/
    @Autowired
    private BillTransformation billTransformation;
    
    /**ProductTransformation, used as a utility regarding the product's transformation.*/
    @Autowired
    private ProductTransformation productTransformation;
	
	/**
	 * The toTransactionCanonical(Transaction) method will transform a Transaction into a TransactionCanonical.
	 * 
	 * @param transaction			- The Transaction to be transformed.
	 * @return TransactionCanonical - The transformed TransactionCanonical.
	 */
	public TransactionCanonical toTransactionCanonical(Transaction transaction) {
		return TransactionCanonical
				.builder()
				.id(transaction.getId())
				.sale(saleTransformation.convert(transaction.getSale()))
				.bill(billTransformation.convert(transaction.getBill()))
				.product(productTransformation.convert(transaction.getProduct()))
				.transactionType(transaction.getTransactionType())
				.quantity(transaction.getQuantity())
				.price(transaction.getPrice())
				.activated(transaction.isActivated())
				.creationDate(transaction.getCreationDate())
				.lastUpdate(transaction.getLastUpdate())
				.build();
	}
	
	/**
	 * The toTransaction(TransactionCanonical) method will transform a TransactionCanonical into a Transaction.
	 * 
	 * @param transaction			- The TransactionCanonical to be transformed.
	 * @return Transaction			- The transformed Transaction.
	 */
	public Transaction toTransaction(TransactionCanonical transaction) {
		return Transaction
				.builder()
				.id(transaction.getId())
				.sale(saleTransformation.convert(transaction.getSale()))
				.bill(billTransformation.convert(transaction.getBill()))
				.product(productTransformation.convert(transaction.getProduct()))
				.transactionType(transaction.getTransactionType())
				.quantity(transaction.getQuantity())
				.price(transaction.getPrice())
				.activated(transaction.isActivated())
				.creationDate(transaction.getCreationDate())
				.lastUpdate(transaction.getLastUpdate())
				.build();
	}
}
