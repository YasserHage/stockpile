package com.stockpile.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stockpile.canonicals.ProductCanonical;
import com.stockpile.domains.Product;
import com.stockpile.services.CompanyService;
import com.stockpile.transformations.CompanyTransformation;

@Service
public class ProductBeanUtil {

	/**CompanyTransformation, used as a utility regarding the company's transformation.*/
    @Autowired
    private CompanyTransformation companyTransformation;
	
	/**
	 * The toProductCanonical(Product) method will transform a Product into a ProductCanonical.
	 * 
	 * @param product			- The Product to be transformed.
	 * @return ProductCanonical - The transformed ProductCanonical.
	 */
	public ProductCanonical toProductCanonical(Product product) {
		return ProductCanonical
				.builder()
				.id(product.getId())
				.name(product.getName())
				.description(product.getDescription())
				.quantity(product.getQuantity())
				.price(product.getPrice())
				.company(companyTransformation.convert(product.getCompany()))
				.imageUrl(product.getImageUrl())
				.activated(product.isActivated())
				.creationDate(product.getCreationDate())
				.lastUpdate(product.getLastUpdate())
				.build();
	}
	
	/**
	 * The toProduct(ProductCanonical) method will transform a ProductCanonical into a Product.
	 * 
	 * @param product			- The ProductCanonical to be transformed.
	 * @return Product			- The transformed Product.
	 */
	public Product toProduct(ProductCanonical product) {
		return Product
				.builder()
				.id(product.getId())
				.name(product.getName())
				.description(product.getDescription())
				.quantity(product.getQuantity())
				.price(product.getPrice())
				.company(companyTransformation.convert(product.getCompany()))
				.imageUrl(product.getImageUrl())
				.activated(product.isActivated())
				.creationDate(product.getCreationDate())
				.lastUpdate(product.getLastUpdate())
				.build();
	}
	
}
