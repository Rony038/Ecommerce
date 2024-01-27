
package com.project.ecommerce.service;


import java.util.List;

import com.project.ecommerce.dto.ProductDto;

public interface ProductService {
    // create
	ProductDto createProduct(ProductDto dto);

	// retrieve;
	ProductDto getProduct(int id);

	List<ProductDto> getAllProduct();

	// update
	ProductDto updateProduct(ProductDto dto, int id);

	// delete
	void deleteProduct(int id);
}
