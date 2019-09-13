package com.stockpile.transformations;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stockpile.canonicals.BillCanonical;
import com.stockpile.domains.Bill;
import com.stockpile.utils.BillBeanUtil;

@Service
public class BillTransformation {

	/**
	 * BillBeanUtil class is a kind of utilities regarding the bill's bean between Bill and BillCanonical.
	 */
	@Autowired
	private BillBeanUtil billBeanUtil;
	
	/**
	 * This convert(Bill) method will transform a Bill into a BillCanonical.
	 * 
	 * @param bill			- the Bill that will be transformed into a BillCanonical.
	 * @return BillCanonical - the transformed BillCanonical.
	 */
	public BillCanonical convert(Bill bill) {
		return this.billBeanUtil.toBillCanonical(bill);
	}
	
	/**
	 * This convert(BillCanonical) method will transform a BillCanonical into a Bill.
	 * 
	 * @param bill			- the BillCanonical that will be transformed into a Bill.
	 * @return Bill			- the transformed Bill.
	 */
	public Bill convert(BillCanonical bill) {
		return this.billBeanUtil.toBill(bill);
	}
	
	/**
	 * It will transform Collection<Bill> into List<BillCanonical>.
	 * 
	 * @param bills					- the collection that will be transformed into List<BillCanonical>.
	 * @return List<BillCanonical>	- the transformed List<BillCanonical>.
	 */
	public List<BillCanonical> convert(Collection<Bill> bills){
		return bills
				.stream()
				.map(this :: convert)
				.collect(Collectors.toList());
	}
}
