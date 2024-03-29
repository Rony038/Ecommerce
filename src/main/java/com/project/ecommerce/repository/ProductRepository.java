package com.project.ecommerce.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.ecommerce.model.Product;


public interface ProductRepository extends JpaRepository<Product, Integer>{
	public List<Product> findByProductNameContaining(String productName);
}
