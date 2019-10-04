package com.stockpile.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.stockpile.domains.Login;

@Repository
public interface LoginRepository extends PagingAndSortingRepository<Login, Integer>{
	Page<Login> findByActivated(Boolean activated, Pageable pageable);
}
