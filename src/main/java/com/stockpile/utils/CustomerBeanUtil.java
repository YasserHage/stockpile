package com.stockpile.utils;

import org.springframework.stereotype.Service;

import com.stockpile.canonicals.CustomerCanonical;
import com.stockpile.domains.Customer;

@Service
public class CustomerBeanUtil {

	/**
	 * The toCustomerCanonical(Customer) method will transform a Customer into a CustomerCanonical.
	 * 
	 * @param customer			- The Customer to be transformed.
	 * @return CustomerCanonical - The transformed CustomerCanonical.
	 */
	public CustomerCanonical toCustomerCanonical(Customer customer) {
		return CustomerCanonical
				.builder()
				.id(customer.getId())
				.name(customer.getName())
				.cpf(customer.getCpf())
				.observation(customer.getObservation())
				.activated(customer.isActivated())
				.creationDate(customer.getCreationDate())
				.lastUpdate(customer.getLastUpdate())
				.build();
	}
	
	/**
	 * The toCustomer(CustomerCanonical) method will transform a CustomerCanonical into a Customer.
	 * 
	 * @param customer			- The CustomerCanonical to be transformed.
	 * @return Customer			- The transformed Customer.
	 */
	public Customer toCustomer(CustomerCanonical customer) {
		return Customer
				.builder()
				.id(customer.getId())
				.name(customer.getName())
				.cpf(customer.getCpf())
				.observation(customer.getObservation())
				.activated(customer.isActivated())
				.creationDate(customer.getCreationDate())
				.lastUpdate(customer.getLastUpdate())
				.build();
	}
	
}
