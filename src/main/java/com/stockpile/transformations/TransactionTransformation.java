package com.stockpile.transformations;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stockpile.canonicals.TransactionCanonical;
import com.stockpile.domains.Transaction;
import com.stockpile.utils.TransactionBeanUtil;

@Service
public class TransactionTransformation {

	/**
	 * TransactionBeanUtil class is a kind of utilities regarding the transaction's bean between Transaction and TransactionCanonical.
	 */
	@Autowired
	private TransactionBeanUtil transactionBeanUtil;
	
	/**
	 * This convert(Transaction) method will transform a Transaction into a TransactionCanonical.
	 * 
	 * @param transaction			- the Transaction that will be transformed into a TransactionCanonical.
	 * @return TransactionCanonical - the transformed TransactionCanonical.
	 */
	public TransactionCanonical convert(Transaction transaction) {
		return this.transactionBeanUtil.toTransactionCanonical(transaction);
	}
	
	/**
	 * This convert(TransactionCanonical) method will transform a TransactionCanonical into a Transaction.
	 * 
	 * @param transaction			- the TransactionCanonical that will be transformed into a Transaction.
	 * @return Transaction			- the transformed Transaction.
	 */
	public Transaction convert(TransactionCanonical transaction) {
		return this.transactionBeanUtil.toTransaction(transaction);
	}
	
	/**
	 * It will transform Collection<Transaction> into List<TransactionCanonical>.
	 * 
	 * @param transactions					- the collection that will be transformed into List<TransactionCanonical>.
	 * @return List<TransactionCanonical>	- the transformed List<TransactionCanonical>.
	 */
	public List<TransactionCanonical> convert(Collection<Transaction> transactions){
		return transactions
				.stream()
				.map(this :: convert)
				.collect(Collectors.toList());
	}
}
