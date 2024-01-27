package com.project.ecommerce.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class OrderDto {
	private int orderId;
    private int userId;
    private List<OrderDetailsDto> orderDetails;
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public List<OrderDetailsDto> getOrderDetails() {
		return orderDetails;
	}
	public void setOrderDetails(List<OrderDetailsDto> orderDetails) {
		this.orderDetails = orderDetails;
	}
	@Override
	public String toString() {
		return "OrderDto [orderId=" + orderId + ", userId=" + userId + ", orderDetails=" + orderDetails + "]";
	}
    
    
}
