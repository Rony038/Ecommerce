package com.project.ecommerce.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ShoppingCartDto {
	private int cartId;
    private int userId;
    private List<CartItemDto> cartItems;
	public int getCartId() {
		return cartId;
	}
	public void setCartId(int cartId) {
		this.cartId = cartId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public List<CartItemDto> getCartItems() {
		return cartItems;
	}
	public void setCartItems(List<CartItemDto> cartItems) {
		this.cartItems = cartItems;
	}
	@Override
	public String toString() {
		return "ShoppingCartDto [cartId=" + cartId + ", userId=" + userId + ", cartItems=" + cartItems + "]";
	}
    
    
}
