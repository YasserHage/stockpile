package com.stockpile.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.stockpile.domains.Contact;

public interface ContactRepository extends PagingAndSortingRepository<Contact, Integer>{
	Page<Contact> findByActivated(Boolean activated, Pageable pageable);
}
