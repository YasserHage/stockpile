package com.stockpile.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stockpile.canonicals.CustomerCanonical;
import com.stockpile.canonicals.SaleCanonical;
import com.stockpile.canonicals.UserCanonical;
import com.stockpile.domains.Sale;
import com.stockpile.transformations.CustomerTransformation;
import com.stockpile.transformations.UserTransformation;

@Service
public class SaleBeanUtil {
	
	/**
	 * The toSaleCanonical(Sale) method will transform a Sale into a SaleCanonical.
	 * 
	 * @param sale			- The Sale to be transformed.
	 * @return SaleCanonical - The transformed SaleCanonical.
	 */
	public SaleCanonical toSaleCanonical(Sale sale) {
		return SaleCanonical
				.builder()
				.id(sale.getId())
				.user(UserCanonical.builder().id(sale.getUser()).build())
				.customer(CustomerCanonical.builder().id(sale.getCustomer()).build())
				.payingMethod(sale.getPayingMethod())
				.activated(sale.isActivated())
				.creationDate(sale.getCreationDate())
				.lastUpdate(sale.getLastUpdate())
				.build();
	}
	
	/**
	 * The toSale(SaleCanonical) method will transform a SaleCanonical into a Sale.
	 * 
	 * @param sale			- The SaleCanonical to be transformed.
	 * @return Sale			- The transformed Sale.
	 */
	public Sale toSale(SaleCanonical sale) {
		return Sale
				.builder()
				.id(sale.getId())
				.user(sale.getUser().getId())
				.customer(sale.getCustomer().getId())
				.payingMethod(sale.getPayingMethod())
				.activated(sale.isActivated())
				.creationDate(sale.getCreationDate())
				.lastUpdate(sale.getLastUpdate())
				.build();
	}
	
}
