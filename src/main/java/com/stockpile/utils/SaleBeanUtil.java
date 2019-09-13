package com.stockpile.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stockpile.canonicals.SaleCanonical;
import com.stockpile.domains.Sale;
import com.stockpile.transformations.CustomerTransformation;
import com.stockpile.transformations.UserTransformation;

@Service
public class SaleBeanUtil {

	/**UserTransformation, used as a utility regarding the user's transformation.*/
    @Autowired
    private UserTransformation userTransformation;
    
    /**CustomerTransformation, used as a utility regarding the customer's transformation.*/
    @Autowired
    private CustomerTransformation customerTransformation;
	
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
				.user(userTransformation.convert(sale.getUser()))
				.customer(customerTransformation.convert(sale.getCustomer()))
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
				.user(userTransformation.convert(sale.getUser()))
				.customer(customerTransformation.convert(sale.getCustomer()))
				.payingMethod(sale.getPayingMethod())
				.activated(sale.isActivated())
				.creationDate(sale.getCreationDate())
				.lastUpdate(sale.getLastUpdate())
				.build();
	}
	
}
