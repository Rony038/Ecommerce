package com.project.ecommerce.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.ecommerce.dto.CartItemDto;
import com.project.ecommerce.dto.ProductDto;
import com.project.ecommerce.dto.ShoppingCartDto;
import com.project.ecommerce.dto.UserDto;
import com.project.ecommerce.exception.ResourceNotFoundException;
import com.project.ecommerce.model.CartItem;
import com.project.ecommerce.model.Product;
import com.project.ecommerce.model.ShoppingCart;
import com.project.ecommerce.model.User;
import com.project.ecommerce.repository.ProductRepository;
import com.project.ecommerce.repository.ShoppingCartRepository;
import com.project.ecommerce.repository.UserRepository;

import jakarta.validation.Valid;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
	@Autowired
	private ShoppingCartRepository repository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
    public ShoppingCartDto getShoppingCartByUserId(int userId) {
        ShoppingCart shoppingCart = this.repository.findByUserUserId(userId)
                .orElseGet(() -> createShoppingCartForUser(userId));

        return this.convertToDto(shoppingCart);
    }
	
	@Override
    public ShoppingCartDto addToCart(int userId, CartItemDto cartItemDto) {
        ShoppingCart shoppingCart = getOrCreateShoppingCart(userId);
       
        Product product = this.productRepository.findById(cartItemDto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product", "ID", cartItemDto.getProductId()));

        CartItem cartItem = this.getCartItemByProductId(product.getProductId(), shoppingCart.getCartItems());

        if (cartItem != null) {
            // Product is already in the cart, update quantity
            cartItem.setQuantity(cartItemDto.getQuantity());
        } else {
            // Product is not in the cart, create a new cart item
            cartItem = new CartItem();
            cartItem.setShoppingCart(shoppingCart);
            cartItem.setProduct(product);
            cartItem.setQuantity(cartItemDto.getQuantity());
            shoppingCart.getCartItems().add(cartItem);
        }
        return this.convertToDto(this.repository.save(shoppingCart));
    }

	@Override
    public ShoppingCartDto updateCart(int userId, List<CartItemDto> cartItems) {
        ShoppingCart shoppingCart = getOrCreateShoppingCart(userId);

        for (CartItemDto cartItemDto : cartItems) {
            Product product = this.productRepository.findById(cartItemDto.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product", "ID", cartItemDto.getProductId()));

            CartItem cartItem = this.getCartItemByProductId(product.getProductId(), shoppingCart.getCartItems());

            if (cartItem != null) {
                cartItem.setQuantity(cartItemDto.getQuantity());
            } else {
                cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setShoppingCart(shoppingCart);
                cartItem.setQuantity(cartItemDto.getQuantity());
                shoppingCart.getCartItems().add(cartItem);
            }
        }
        return this.convertToDto(this.repository.save(shoppingCart));
    }

    @Override
    public ShoppingCartDto removeFromCart(int userId, int productId) {
        ShoppingCart shoppingCart = getOrCreateShoppingCart(userId);
        shoppingCart.getCartItems().removeIf(item -> item.getProduct().getProductId()==productId);
       return this.convertToDto(this.repository.save(shoppingCart));
    }

    private ShoppingCart getOrCreateShoppingCart(int userId) {
        return this.repository.findByUserUserId(userId)
                .orElseGet(() -> createShoppingCartForUser(userId));
    }
    
    private ShoppingCart createShoppingCartForUser(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User","ID", userId));

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        return this.repository.save(shoppingCart);
    }
    
    public CartItem getCartItemByProductId(int productId, List<CartItem> cartItems) {
        if(cartItems!=null) {
        	for (CartItem cartItem : cartItems) {
                if (cartItem.getProduct().getProductId()==productId) {
                    return cartItem;
                }
            }
        }
        return null;
    }
    
    @Override
    public ShoppingCartDto getShoppingCart(int userId, int cartId) {
        ShoppingCart shoppingCart = this.repository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Shopping Cart", " Id ", cartId));
        return this.convertToDto(shoppingCart);
    }
	public ShoppingCartDto convertToDto(ShoppingCart shoppingCart) {
        return this.modelMapper.map(shoppingCart, ShoppingCartDto.class);

    }

    public ShoppingCart convertToShoppingCart(ShoppingCartDto dto) {
        return this.modelMapper.map(dto, ShoppingCart.class);

    }

}
