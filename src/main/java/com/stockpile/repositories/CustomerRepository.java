package com.stockpile.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.stockpile.domains.Customer;


@Repository
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Integer>{
	Page<Customer> findByActivated(Boolean activated, Pageable pageable);
}
