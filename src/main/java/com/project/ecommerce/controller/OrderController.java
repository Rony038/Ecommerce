package com.project.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.ecommerce.dto.OrderDto;
import com.project.ecommerce.dto.ProductDto;
import com.project.ecommerce.service.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/Order")
public class OrderController {
	@Autowired
	private OrderService service;
	@PostMapping("/process-order")
	public ResponseEntity<OrderDto> processOrder(@Valid @RequestParam("userId") int userId,
            @RequestParam("cartId") int cartId) {
		return new ResponseEntity<OrderDto>(this.service.processOrder(userId, cartId), HttpStatus.CREATED);
	}
}
