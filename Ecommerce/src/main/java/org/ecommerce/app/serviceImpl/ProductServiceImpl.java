package org.ecommerce.app.serviceImpl;

import org.ecommerce.app.exceptions.APIException;
import org.ecommerce.app.exceptions.ResourceNotFoundException;
import org.ecommerce.app.model.Category;
import org.ecommerce.app.model.Product;
import org.ecommerce.app.payload.product.ProductDTO;
import org.ecommerce.app.payload.product.ProductResponse;
import org.ecommerce.app.repository.CategoryRepository;
import org.ecommerce.app.repository.ProductRepository;
import org.ecommerce.app.service.FileService;
import org.ecommerce.app.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private FileService fileService;
    @Value("${project.images}")
    private String path;

    @Override
    public ProductDTO addProduct(Long categoryId, ProductDTO productDto) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        boolean isPresent = false;
        List<Product> products = category.getProducts();
        for (Product product : products)
            if(product.getProductName().equals(productDto.getProductName())){
                isPresent = true;
                break;
            }
        if(isPresent)
            throw new APIException("Product "+productDto.getProductName()+" already exists");

        Product product = modelMapper.map(productDto, Product.class);
        product.setCategory(category);
        double specialPrice = (product.getPrice() - ((product.getDiscount() * 0.01) * product.getPrice()));
        product.setSpecialPrice(specialPrice);
        product.setImage("images/products/default.jpg");

        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductResponse getAllProducts() {
        List<Product> products = productRepository.findAll();

        if (products.isEmpty())
            throw new APIException("No products found!");

        List<ProductDTO> productDTOS = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();

        return ProductResponse.builder()
                .content(productDTOS)
                .build();
    }

    @Override
    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
    }

    @Override
    public ProductResponse getAllProductsByCategoryId(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        List<Product> products = productRepository.findByCategoryOrderByPriceAsc(category);
        if (products.isEmpty())
            throw new APIException("No products found!");

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
        if (products.isEmpty())
            throw new APIException("No products found!");

        List<ProductDTO> productDTOS = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();

        return ProductResponse.builder()
                .content(productDTOS)
                .build();
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDto) {
        Product product = modelMapper.map(productDto, Product.class);
        Product savedProduct = productRepository.findById(product.getProductId()).
                orElseThrow(() -> new ResourceNotFoundException("Product", "productId", product.getProductId()));

        savedProduct.setProductName(product.getProductName() == null  ? savedProduct.getProductName() : product.getProductName());
        savedProduct.setImage(product.getImage( )== null? savedProduct.getImage() : product.getImage());
        savedProduct.setDescription(product.getDescription() == null ? savedProduct.getDescription() : product.getDescription());
        savedProduct.setQuantity(product.getQuantity() != null ? product.getQuantity() : savedProduct.getQuantity());
        savedProduct.setPrice(product.getPrice() != null ? product.getPrice() : savedProduct.getPrice());
        savedProduct.setDiscount(product.getDiscount() != null ? product.getDiscount() : savedProduct.getDiscount());
        double specialPrice = (savedProduct.getPrice() - ((savedProduct.getDiscount() * 0.01) * savedProduct.getPrice()));
        savedProduct.setSpecialPrice(specialPrice);

        return modelMapper.map(productRepository.save(savedProduct), ProductDTO.class);
    }

    @Override
    public ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException {
        Product product = getProductById(productId);
        String fileName = fileService.uploadImage(path, image);
        product.setImage(fileName);
        return modelMapper.map(productRepository.save(product),
                ProductDTO.class);
    }
    @Override
    public ProductDTO deleteProduct(Long productId) {
        Product product = getProductById(productId);
        productRepository.delete(product);
        return modelMapper.map(product, ProductDTO.class);
    }
}
