package com.project.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.ecommerce.model.ShoppingCart;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer> {
	Optional<ShoppingCart> findByUserUserId(int userId);
}
