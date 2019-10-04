package com.stockpile.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.stockpile.domains.Contact;

@Repository
public interface ContactRepository extends PagingAndSortingRepository<Contact, Integer>{
	Page<Contact> findByActivated(Boolean activated, Pageable pageable);
}
