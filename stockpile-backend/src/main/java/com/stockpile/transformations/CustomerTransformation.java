package com.stockpile.transformations;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stockpile.canonicals.CustomerCanonical;
import com.stockpile.domains.Customer;
import com.stockpile.utils.CustomerBeanUtil;

@Service
public class CustomerTransformation {

	/**
	 * CustomerBeanUtil class is a kind of utilities regarding the customer's bean between Customer and CustomerCanonical.
	 */
	@Autowired
	private CustomerBeanUtil customerBeanUtil;
	
	/**
	 * This convert(Customer) method will transform a Customer into a CustomerCanonical.
	 * 
	 * @param customer			- the Customer that will be transformed into a CustomerCanonical.
	 * @return CustomerCanonical - the transformed CustomerCanonical.
	 */
	public CustomerCanonical convert(Customer customer) {
		return this.customerBeanUtil.toCustomerCanonical(customer);
	}
	
	/**
	 * This convert(CustomerCanonical) method will transform a CustomerCanonical into a Customer.
	 * 
	 * @param customer			- the CustomerCanonical that will be transformed into a Customer.
	 * @return Customer			- the transformed Customer.
	 */
	public Customer convert(CustomerCanonical customer) {
		return this.customerBeanUtil.toCustomer(customer);
	}
	
	/**
	 * It will transform Collection<Customer> into List<CustomerCanonical>.
	 * 
	 * @param customers					- the collection that will be transformed into List<CustomerCanonical>.
	 * @return List<CustomerCanonical>	- the transformed List<CustomerCanonical>.
	 */
	public List<CustomerCanonical> convert(Collection<Customer> customers){
		return customers
				.stream()
				.map(this :: convert)
				.collect(Collectors.toList());
	}
}
