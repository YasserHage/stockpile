package com.stockpile.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.stockpile.domains.Sale;

@Repository
public interface SaleRepository extends PagingAndSortingRepository<Sale, Integer> {
	Page<Sale> findByActivated(Boolean activated, Pageable pageable);
}
