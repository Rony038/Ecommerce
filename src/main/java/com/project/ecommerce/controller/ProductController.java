package com.project.ecommerce.controller;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.ecommerce.dto.ProductDto;
import com.project.ecommerce.exception.ResourceNotFoundException;
import com.project.ecommerce.model.Product;
import com.project.ecommerce.service.ImageService;
import com.project.ecommerce.service.ProductService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/product")
public class ProductController {
	@Autowired
	private ProductService service;

	@Autowired
	private ImageService imageService;

	// create
	@PostMapping("/save")
	public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) throws IOException {
		return new ResponseEntity<ProductDto>(this.service.createProduct(productDto), HttpStatus.CREATED);
	}

	// retrieve
	@GetMapping("/{id}")
	public ResponseEntity<Product> getProduct(@PathVariable("id") int id) {
		return new ResponseEntity<Product>(this.service.getProduct(id), HttpStatus.OK);

	}

	@GetMapping("/")
	public ResponseEntity<List<Product>> getAllProduct() {
		return ResponseEntity.ok(this.service.getAllProduct());
	}

	// update
	@PutMapping("/update/{id}")
	public ResponseEntity<ProductDto> updateProduct(@Valid @RequestBody ProductDto productDto,
			@PathVariable("id") int id) throws IOException {
		// return new ResponseEntity<ProductDto>(this.service.updateProduct(productDto,
		// id), HttpStatus.CREATED);
		try {
			ProductDto updatedProduct = this.service.updateProduct(productDto, id);
			return ResponseEntity.ok(updatedProduct);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	// delete
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable("id") int id) {
		this.service.deleteProduct(id);
		return ResponseEntity.ok("Product Deleted");
	}

	// Search post
	@GetMapping("/search/{keywords}")
	public ResponseEntity<List<ProductDto>> getProductByid(@PathVariable String keywords) {
		return ResponseEntity.ok(this.service.searchProduct(keywords));
	}
}
