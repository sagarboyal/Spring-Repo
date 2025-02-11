package org.ecommerce.app.serviceImpl;

import org.ecommerce.app.exceptions.ResourceNotFoundException;
import org.ecommerce.app.model.Category;
import org.ecommerce.app.model.Product;
import org.ecommerce.app.payload.product.ProductDTO;
import org.ecommerce.app.payload.product.ProductResponse;
import org.ecommerce.app.repository.CategoryRepository;
import org.ecommerce.app.repository.ProductRepository;
import org.ecommerce.app.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDTO addProduct(Long categoryId, Product product) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        product.setCategory(category);
        double specialPrice = (product.getPrice() - ((product.getDiscount() * 0.01) * product.getPrice()));
        product.setSpecialPrice(specialPrice);
        product.setImageUrl("images/products/default.jpg");

        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductResponse getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOS = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();

        return ProductResponse.builder()
                .content(productDTOS)
                .build();
    }

    @Override
    public ProductResponse getAllProductsByCategoryId(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        List<Product> products = productRepository.findByCategoryOrderByPriceAsc(category);
        List<ProductDTO> productDTOS = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();

        return ProductResponse.builder()
                .content(productDTOS)
                .build();
    }

    @Override
    public ProductResponse getAllProductsByCategoryKeyword(String keyword) {
        List<Product> products = productRepository.findByProductNameLikeIgnoreCase('%' + keyword + '%');
        List<ProductDTO> productDTOS = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();

        return ProductResponse.builder()
                .content(productDTOS)
                .build();
    }

    @Override
    public ProductDTO updateProduct(Product product) {
        Product savedProduct = productRepository.findById(product.getProductId()).
                orElseThrow(() -> new ResourceNotFoundException("Product", "productId", product.getProductId()));

        savedProduct.setProductName(product.getProductName() == null  ? savedProduct.getProductName() : product.getProductName());
        savedProduct.setImageUrl(product.getImageUrl( )== null? savedProduct.getImageUrl() : product.getImageUrl());
        savedProduct.setDescription(product.getDescription() == null ? savedProduct.getDescription() : product.getDescription());
        savedProduct.setQuantity(product.getQuantity() != null ? product.getQuantity() : savedProduct.getQuantity());
        savedProduct.setPrice(product.getPrice() != null ? product.getPrice() : savedProduct.getPrice());
        savedProduct.setDiscount(product.getDiscount() != null ? product.getDiscount() : savedProduct.getDiscount());
        double specialPrice = (savedProduct.getPrice() - ((savedProduct.getDiscount() * 0.01) * savedProduct.getPrice()));
        savedProduct.setSpecialPrice(specialPrice);

        return modelMapper.map(productRepository.save(savedProduct), ProductDTO.class);
    }
}
