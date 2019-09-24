package com.stockpile.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stockpile.canonicals.BillCanonical;
import com.stockpile.canonicals.CompanyCanonical;
import com.stockpile.canonicals.UserCanonical;
import com.stockpile.domains.Bill;
import com.stockpile.transformations.CompanyTransformation;
import com.stockpile.transformations.UserTransformation;

@Service
public class BillBeanUtil {
	
	/**
	 * The toBillCanonical(Bill) method will transform a Bill into a BillCanonical.
	 * 
	 * @param bill			- The Bill to be transformed.
	 * @return BillCanonical - The transformed BillCanonical.
	 */
	public BillCanonical toBillCanonical(Bill bill) {
		return BillCanonical
				.builder()
				.id(bill.getId())
				.user(UserCanonical.builder().id(bill.getUser()).build())
				.company(CompanyCanonical.builder().id(bill.getCompany()).build())
				.payingMethod(bill.getPayingMethod())
				.activated(bill.isActivated())
				.creationDate(bill.getCreationDate())
				.lastUpdate(bill.getLastUpdate())
				.build();
	}
	
	/**
	 * The toBill(BillCanonical) method will transform a BillCanonical into a Bill.
	 * 
	 * @param bill			- The BillCanonical to be transformed.
	 * @return Bill			- The transformed Bill.
	 */
	public Bill toBill(BillCanonical bill) {
		return Bill
				.builder()
				.id(bill.getId())
				.user(bill.getUser().getId())
				.company(bill.getCompany().getId())
				.payingMethod(bill.getPayingMethod())
				.activated(bill.isActivated())
				.creationDate(bill.getCreationDate())
				.lastUpdate(bill.getLastUpdate())
				.build();
	}
	
}
