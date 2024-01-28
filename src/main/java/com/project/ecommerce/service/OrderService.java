package com.project.ecommerce.service;

import com.project.ecommerce.dto.OrderDto;

import jakarta.validation.Valid;

public interface OrderService {

	OrderDto processOrder(@Valid int userId);

}
