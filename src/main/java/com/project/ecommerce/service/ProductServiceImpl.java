package com.project.ecommerce.service;


import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    
    @Autowired
    private ImageService imageService;

    @Override
    public ProductDto createProduct(ProductDto dto) throws IOException {
    	// Handle image upload
    	String imageUrl = imageService.store(dto.getProductImage());
        
        Product product = this.convertToProduct(dto);
        
        product.setPrice(dto.getPrice());
        product.setProductDesc(dto.getProductDesc());
        product.setProductName(dto.getProductName());
		product.setImageUrl(imageUrl);
    	
        return this.convertToDto(this.repository.save(product));
    }

    @Override
    public Optional<Product> getProduct(int id)throws ResourceNotFoundException {
        Optional<Product> optionalProduct = this.repository.findById(id);
        
        return optionalProduct;
    }

    @Override
    public List<Product> getAllProduct() {
        List<Product> productList = this.repository.findAll();
        return productList;
    }

    @Override
    public ProductDto updateProduct(ProductDto dto, int id) throws IOException {
//        Product product = this.repository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Product", " Id ", id));
//        
//        product.setPrice(dto.getPrice());
//        product.setProductDesc(dto.getProductDesc());
//        product.setProductName(dto.getProductName());
//		product.setProductImage(dto.getProductImage());
//
//        return this.convertToDto(this.repository.save(product));
    	
    	Optional<Product> optionalProduct = this.repository.findById(id);
        Product product = optionalProduct.orElseThrow(() -> new ResourceNotFoundException("Product", " Id ", id));
        
        product.setProductName(dto.getProductName());
        product.setProductDesc(dto.getProductDesc());
        product.setPrice(dto.getPrice());
        
        // Update image if provided
        MultipartFile imageFile = dto.getProductImage();
        if (imageFile != null && !imageFile.isEmpty()) {
        	String imageUrl = this.imageService.store(dto.getProductImage());
            product.setImageUrl(imageUrl);
        }
        
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

    public ProductDto convertToOptionalDto(Optional<Product> optionalProduct) {
        return this.modelMapper.map(optionalProduct, ProductDto.class);

    }
    public Product convertToProduct(ProductDto dto) {
        return this.modelMapper.map(dto, Product.class);

    }

}
