package com.stockpile.utils;

import org.springframework.stereotype.Service;

import com.stockpile.canonicals.CompanyCanonical;
import com.stockpile.domains.Company;


@Service
public class CompanyBeanUtil {

	/**
	 * The toCompanyCanonical(Company) method will transform a Company into a CompanyCanonical.
	 * 
	 * @param company			- The Company to be transformed.
	 * @return CompanyCanonical - The transformed CompanyCanonical.
	 */
	public CompanyCanonical toCompanyCanonical(Company company) {
		return CompanyCanonical
				.builder()
				.id(company.getId())
				.name(company.getName())
				.description(company.getDescription())
				.activated(company.isActivated())
				.creationDate(company.getCreationDate())
				.lastUpdate(company.getLastUpdate())
				.build();
	}
	
	/**
	 * The toCompany(CompanyCanonical) method will transform a CompanyCanonical into a Company.
	 * 
	 * @param company			- The CompanyCanonical to be transformed.
	 * @return Company			- The transformed Company.
	 */
	public Company toCompany(CompanyCanonical company) {
		return Company
				.builder()
				.id(company.getId())
				.name(company.getName())
				.description(company.getDescription())
				.activated(company.isActivated())
				.creationDate(company.getCreationDate())
				.lastUpdate(company.getLastUpdate())
				.build();
	}
}
