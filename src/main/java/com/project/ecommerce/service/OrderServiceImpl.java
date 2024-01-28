package com.project.ecommerce.service;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.ecommerce.dto.OrderDto;
import com.project.ecommerce.model.OrderDetails;
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

import jakarta.transaction.Transactional;
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
	
//	@Override
//	public OrderDto processOrder(@Valid int userId, int cartId) {
//		User user = this.userRepo.findById(userId)
//                .orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId));
//		
//		
//		Optional<ShoppingCart> shoppingCart = this.shoppingCartRepository.findById(cartId);
//		//List<OrderDetails> orderDetail = new ArrayList<OrderDetails>();
//		OrderDetails od = new OrderDetails();
//		List<OrderDetails> list=new ArrayList<OrderDetails>();  
//		Order order = new Order();
//        order.setUser(user);
//		for (CartItem cartItem : shoppingCart.get().getCartItems()) {
//			list.setOrder(order);
//			list.setProduct(cartItem.getProduct());
//			list.setQuantity(cartItem.getQuantity());
//            // Set other details like price, etc.
//            order.getOrderDetails().add(list);
//        }
//	}
	
	@Transactional
	@Override
    public OrderDto processOrder(@Valid int userId) {
		System.out.println("UserId"+ userId);
		User user = this.userRepo.findById(userId)
              .orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId));
		System.out.println("user:"+user);
        // Retrieve the user's shopping cart
		ShoppingCart shoppingCart = this.shoppingCartRepository.findByUserUserId(user.getUserId())
				.orElseThrow(() ->new ResourceNotFoundException("Shopping Cart", "User ID", user.getUserId()));
		Order order = new Order();
        if (shoppingCart != null) {
            order.setUser(user);
            // Iterate through cartItems and move them to orderDetails
            List<CartItem> cartItems = shoppingCart.getCartItems();
            List<OrderDetails> orderDetailsList = new ArrayList<>();
            
            for (CartItem cartItem : cartItems) {
                OrderDetails orderDetails = new OrderDetails();
                orderDetails.setOrder(order);
                orderDetails.setProduct(cartItem.getProduct());
                orderDetails.setPrice(cartItem.getProduct().getPrice());
                orderDetails.setQuantity(cartItem.getQuantity());
                orderDetailsList.add(orderDetails);
            }
            System.out.println(orderDetailsList);
            order.setOrderDetails(orderDetailsList);
            // Remove cartItems from the shopping cart
            shoppingCart.getCartItems().clear();
            shoppingCartRepository.save(shoppingCart);
            
        }
        return this.convertToDto(this.repository.save(order));
    }
	
	
	public OrderDto convertToDto(Order order) {
        return this.modelMapper.map(order, OrderDto.class);

    }

    public Order convertToOrder(OrderDto dto) {
        return this.modelMapper.map(dto, Order.class);

    }


}
