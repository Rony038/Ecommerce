package com.project.ecommerce.dto;



public class CartItemDto {
	private int cartItemId;
    private int productId;
    private String productName;
    private int quantity;
	public int getCartItemId() {
		return cartItemId;
	}
	public void setCartItemId(int cartItemId) {
		this.cartItemId = cartItemId;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	@Override
	public String toString() {
		return "CartItemDto [cartItemId=" + cartItemId + ", productId=" + productId + ", productName=" + productName
				+ ", quantity=" + quantity + "]";
	}
    
    
}
