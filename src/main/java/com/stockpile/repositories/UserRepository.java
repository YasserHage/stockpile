package com.stockpile.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import com.stockpile.domains.User;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Integer>{
	Page<User> findByActivated(Boolean activated, Pageable pageable);
}
