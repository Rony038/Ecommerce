package com.project.ecommerce.dto;

import java.util.Arrays;

import com.project.ecommerce.model.Product;

public class ProductWithImageDataDto {
	private Product product;
    private byte[] imageData;
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public byte[] getImageData() {
		return imageData;
	}
	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}
	public ProductWithImageDataDto(Product product, byte[] imageData) {
		super();
		this.product = product;
		this.imageData = imageData;
	}
	public ProductWithImageDataDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "ProductWithImageDataDto [product=" + product + ", imageData=" + Arrays.toString(imageData) + "]";
	}
    
    
}
