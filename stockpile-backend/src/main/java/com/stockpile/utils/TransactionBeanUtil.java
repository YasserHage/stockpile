package com.stockpile.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stockpile.canonicals.SaleCanonical;
import com.stockpile.canonicals.BillCanonical;
import com.stockpile.canonicals.ProductCanonical;
import com.stockpile.canonicals.TransactionCanonical;
import com.stockpile.domains.Transaction;

@Service
public class TransactionBeanUtil {
	
	/**
	 * SaleCanonical is a bean class regarding the Sale data output.
	 */
	SaleCanonical saleCanonical;
	
	/**
	 * BillCanonical is a bean class regarding the Bill data output.
	 */
	BillCanonical billCanonical;
	
	/**
	 * The toTransactionCanonical(Transaction) method will transform a Transaction into a TransactionCanonical.
	 * 
	 * @param transaction			- The Transaction to be transformed.
	 * @return TransactionCanonical - The transformed TransactionCanonical.
	 */
	public TransactionCanonical toTransactionCanonical(Transaction transaction) {
		if(transaction.getSale() != null) {
			this.saleCanonical = SaleCanonical
							.builder()
							.id(transaction.getSale())
							.build();
		}else {
			this.saleCanonical = null;
		}
		if(transaction.getBill() != null) {
			this.billCanonical = BillCanonical
							.builder()
							.id(transaction.getBill())
							.build();
		}else {
			this.billCanonical = null;
		}
		return TransactionCanonical
				.builder()
				.id(transaction.getId())
				.sale(this.saleCanonical)
				.bill(this.billCanonical)
				.product(ProductCanonical.builder().id(transaction.getProduct()).build())
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
		Integer bill = null;
		Integer sale = null;
		if(transaction.getBill() !=null) {
			bill = transaction.getBill().getId();
		}
		if(transaction.getSale() !=null) {
			sale = transaction.getSale().getId();
		}
		return Transaction
				.builder()
				.id(transaction.getId())
				.sale(sale)
				.bill(bill)
				.product(transaction.getProduct().getId())
				.transactionType(transaction.getTransactionType())
				.quantity(transaction.getQuantity())
				.price(transaction.getPrice())
				.activated(transaction.isActivated())
				.creationDate(transaction.getCreationDate())
				.lastUpdate(transaction.getLastUpdate())
				.build();
	}
}
