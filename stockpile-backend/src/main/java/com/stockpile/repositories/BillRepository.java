package com.stockpile.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.stockpile.domains.Bill;

@Repository
public interface BillRepository extends PagingAndSortingRepository<Bill, Integer>{
	Page<Bill> findByActivated(Boolean activated, Pageable pageable);
}
