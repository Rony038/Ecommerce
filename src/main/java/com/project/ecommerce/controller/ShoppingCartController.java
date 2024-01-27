package com.project.ecommerce.controller;

import java.util.List;

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

import com.project.ecommerce.dto.CartItemDto;
import com.project.ecommerce.dto.ProductDto;
import com.project.ecommerce.dto.ShoppingCartDto;
import com.project.ecommerce.service.ShoppingCartService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/shopping-cart")
public class ShoppingCartController {
	@Autowired
	ShoppingCartService service;
	
	@GetMapping("/{userId}")
    public ResponseEntity<ShoppingCartDto> getShoppingCartByUserId(@PathVariable int userId) {
		ShoppingCartDto shoppingCart = this.service.getShoppingCartByUserId(userId);
        return ResponseEntity.ok(shoppingCart);
    }

    @PostMapping("/{userId}/add-to-cart")
    public ResponseEntity<ShoppingCartDto> addToCart(@Valid @PathVariable int userId, @RequestBody CartItemDto cartItemDto) {
    	return new ResponseEntity<ShoppingCartDto>(this.service.addToCart(userId, cartItemDto), HttpStatus.CREATED);
    }

    @PutMapping("/{userId}/update-cart")
    public ResponseEntity<ShoppingCartDto> updateCart(@Valid @PathVariable int userId, @RequestBody List<CartItemDto> cartItems) {
		return new ResponseEntity<ShoppingCartDto>(this.service.updateCart(userId, cartItems), HttpStatus.CREATED);
    }

    @DeleteMapping("/{userId}/remove-from-cart/{productId}")
    public ResponseEntity<ShoppingCartDto> removeFromCart(@PathVariable int userId, @PathVariable int productId) {
    	this.service.removeFromCart(userId, productId);
		return new ResponseEntity<ShoppingCartDto>(this.service.removeFromCart(userId, productId), HttpStatus.CREATED);
    }

    
}
