package com.stockpile.transformations;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stockpile.canonicals.CompanyCanonical;
import com.stockpile.domains.Company;
import com.stockpile.utils.CompanyBeanUtil;

@Service
public class CompanyTransformation {
	
	/**
	 * CompanyBeanUtil class is a kind of utilities regarding the company's bean between Company and CompanyCanonical.
	 */
	@Autowired
	private CompanyBeanUtil companyBeanUtil;
	
	/**
	 * This convert(Company) method will transform a Company into a CompanyCanonical.
	 * 
	 * @param company			- the Company that will be transformed into a CompanyCanonical.
	 * @return CompanyCanonical - the transformed CompanyCanonical.
	 */
	public CompanyCanonical convert(Company company) {
		return this.companyBeanUtil.toCompanyCanonical(company);
	}
	
	/**
	 * This convert(CompanyCanonical) method will transform a CompanyCanonical into a Company.
	 * 
	 * @param company			- the CompanyCanonical that will be transformed into a Company.
	 * @return Company			- the transformed Company.
	 */
	public Company convert(CompanyCanonical company) {
		return this.companyBeanUtil.toCompany(company);
	}
	
	/**
	 * It will transform Collection<Company> into List<CompanyCanonical>.
	 * 
	 * @param companies					- the collection that will be transformed into List<CompanyCanonical>.
	 * @return List<CompanyCanonical>	- the transformed List<CompanyCanonical>.
	 */
	public List<CompanyCanonical> convert(Collection<Company> companies){
		return companies
				.stream()
				.map(this :: convert)
				.collect(Collectors.toList());
	}

}
