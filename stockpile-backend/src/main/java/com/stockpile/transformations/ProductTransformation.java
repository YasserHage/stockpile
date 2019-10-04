package com.stockpile.transformations;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stockpile.canonicals.ProductCanonical;
import com.stockpile.domains.Product;
import com.stockpile.utils.ProductBeanUtil;

@Service
public class ProductTransformation {

	/**
	 * ProductBeanUtil class is a kind of utilities regarding the product's bean between Product and ProductCanonical.
	 */
	@Autowired
	private ProductBeanUtil productBeanUtil;
	
	/**
	 * This convert(Product) method will transform a Product into a ProductCanonical.
	 * 
	 * @param product			- the Product that will be transformed into a ProductCanonical.
	 * @return ProductCanonical - the transformed ProductCanonical.
	 */
	public ProductCanonical convert(Product product) {
		return this.productBeanUtil.toProductCanonical(product);
	}
	
	/**
	 * This convert(ProductCanonical) method will transform a ProductCanonical into a Product.
	 * 
	 * @param product			- the ProductCanonical that will be transformed into a Product.
	 * @return Product			- the transformed Product.
	 */
	public Product convert(ProductCanonical product) {
		return this.productBeanUtil.toProduct(product);
	}
	
	/**
	 * It will transform Collection<Product> into List<ProductCanonical>.
	 * 
	 * @param products					- the collection that will be transformed into List<ProductCanonical>.
	 * @return List<ProductCanonical>	- the transformed List<ProductCanonical>.
	 */
	public List<ProductCanonical> convert(Collection<Product> products){
		return products
				.stream()
				.map(this :: convert)
				.collect(Collectors.toList());
	}
	
}
