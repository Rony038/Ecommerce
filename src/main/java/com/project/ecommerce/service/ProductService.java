
package com.project.ecommerce.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.project.ecommerce.dto.ProductDto;
import com.project.ecommerce.exception.ResourceNotFoundException;
import com.project.ecommerce.model.Product;

public interface ProductService {
	// create
	ProductDto createProduct(ProductDto dto) throws IOException;

	// retrieve;
	Product getProduct(int id);

	List<Product> getAllProduct();

	// update
	ProductDto updateProduct(ProductDto dto, int id) throws IOException;

	// delete
	void deleteProduct(int id);

	// search posts
	List<ProductDto> searchProduct(String keyword);

}
