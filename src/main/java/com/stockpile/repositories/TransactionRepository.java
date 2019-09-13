package com.stockpile.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.stockpile.domains.Transaction;

@Repository
public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Integer>{
	Page<Transaction> findByActivated(Boolean activated, Pageable pageable);
}
