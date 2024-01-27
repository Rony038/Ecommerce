package com.project.ecommerce.service;

import java.util.List;

import com.project.ecommerce.dto.CartItemDto;
import com.project.ecommerce.dto.ShoppingCartDto;

import jakarta.validation.Valid;

public interface ShoppingCartService {

	ShoppingCartDto getShoppingCartByUserId(int userId);

	ShoppingCartDto addToCart(@Valid int userId, CartItemDto cartItemDto);

	ShoppingCartDto updateCart(@Valid int userId, List<CartItemDto> cartItems);

	ShoppingCartDto removeFromCart(int userId, int productId);
	
	public ShoppingCartDto getShoppingCart(int userId, int cartId);

}
