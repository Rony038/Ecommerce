package com.project.ecommerce.service;


import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.ecommerce.dto.ProductDto;
import com.project.ecommerce.exception.ResourceNotFoundException;
import com.project.ecommerce.model.Product;
import com.project.ecommerce.repository.ProductRepository;


@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repository;
    
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDto createProduct(ProductDto dto) {
        return this.convertToDto(this.repository.save(this.convertToProduct(dto)));
    }

    @Override
    public ProductDto getProduct(int id) {
        Product product = this.repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", " Id ", id));
        return this.convertToDto(product);
    }

    @Override
    public List<ProductDto> getAllProduct() {
        List<Product> productList = this.repository.findAll();
        return productList.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public ProductDto updateProduct(ProductDto dto, int id) {
        Product product = this.repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", " Id ", id));
        
        product.setPrice(dto.getPrice());
        product.setProductDesc(dto.getProductDesc());
        product.setProductName(dto.getProductName());
		product.setProductImage(dto.getProductImage());

        return this.convertToDto(this.repository.save(product));
    }

    @Override
    public void deleteProduct(int id) {
        Product product = this.repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", " Id ", id));

        this.repository.delete(product);
    }

    public ProductDto convertToDto(Product product) {
        return this.modelMapper.map(product, ProductDto.class);

    }

    public Product convertToProduct(ProductDto dto) {
        return this.modelMapper.map(dto, Product.class);

    }

}
