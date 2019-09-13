package com.stockpile.transformations;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stockpile.canonicals.SaleCanonical;
import com.stockpile.domains.Sale;
import com.stockpile.utils.SaleBeanUtil;

@Service
public class SaleTransformation {

	/**
	 * SaleBeanUtil class is a kind of utilities regarding the sale's bean between Sale and SaleCanonical.
	 */
	@Autowired
	private SaleBeanUtil saleBeanUtil;
	
	/**
	 * This convert(Sale) method will transform a Sale into a SaleCanonical.
	 * 
	 * @param sale			- the Sale that will be transformed into a SaleCanonical.
	 * @return SaleCanonical - the transformed SaleCanonical.
	 */
	public SaleCanonical convert(Sale sale) {
		return this.saleBeanUtil.toSaleCanonical(sale);
	}
	
	/**
	 * This convert(SaleCanonical) method will transform a SaleCanonical into a Sale.
	 * 
	 * @param sale			- the SaleCanonical that will be transformed into a Sale.
	 * @return Sale			- the transformed Sale.
	 */
	public Sale convert(SaleCanonical sale) {
		return this.saleBeanUtil.toSale(sale);
	}
	
	/**
	 * It will transform Collection<Sale> into List<SaleCanonical>.
	 * 
	 * @param sales					- the collection that will be transformed into List<SaleCanonical>.
	 * @return List<SaleCanonical>	- the transformed List<SaleCanonical>.
	 */
	public List<SaleCanonical> convert(Collection<Sale> sales){
		return sales
				.stream()
				.map(this :: convert)
				.collect(Collectors.toList());
	}
}
