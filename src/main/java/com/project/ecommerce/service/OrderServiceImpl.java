package com.project.ecommerce.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.ecommerce.dto.OrderDto;
import com.project.ecommerce.dto.ShoppingCartDto;
import com.project.ecommerce.exception.ResourceNotFoundException;
import com.project.ecommerce.model.CartItem;
import com.project.ecommerce.model.Order;
import com.project.ecommerce.model.Product;
import com.project.ecommerce.model.ShoppingCart;
import com.project.ecommerce.model.User;
import com.project.ecommerce.repository.OrderRepository;
import com.project.ecommerce.repository.ProductRepository;
import com.project.ecommerce.repository.ShoppingCartRepository;
import com.project.ecommerce.repository.UserRepository;

import jakarta.validation.Valid;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderRepository repository;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private ShoppingCartRepository shoppingCartRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public OrderDto processOrder(@Valid int userId, int cartId) {
		User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId));
		
		
		Optional<ShoppingCart> shoppingCart = this.shoppingCartRepository.findById(cartId);
		//List<OrderDetails> orderDetail = new ArrayList<OrderDetails>();
		List<OrderDetails> list=new ArrayList<OrderDetails>();  
		Order order = new Order();
        order.setUser(user);
		for (CartItem cartItem : shoppingCart.get().getCartItems()) {
			list.setOrder(order);
			list.setProduct(cartItem.getProduct());
			list.setQuantity(cartItem.getQuantity());
            // Set other details like price, etc.
            order.getOrderDetails().add(orderDetail);
        }
	}
	
	
	public OrderDto convertToDto(Order order) {
        return this.modelMapper.map(order, OrderDto.class);

    }

    public Order convertToOrder(OrderDto dto) {
        return this.modelMapper.map(dto, Order.class);

    }


}
