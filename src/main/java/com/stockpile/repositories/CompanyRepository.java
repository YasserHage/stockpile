package com.stockpile.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import com.stockpile.domains.Company;

@Repository
public interface CompanyRepository extends PagingAndSortingRepository<Company, Integer>{
	Page<Company> findByActivated(Boolean activated, Pageable pageable);
}
