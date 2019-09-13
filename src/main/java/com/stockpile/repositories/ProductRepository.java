package com.stockpile.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.stockpile.domains.Product;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Integer> {
	Page<Product> findByActivated(Boolean activated, Pageable pageable);
}
