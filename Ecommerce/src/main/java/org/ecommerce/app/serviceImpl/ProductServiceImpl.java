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

    @Override
    public ProductDTO addProduct(Long categoryId, ProductDTO productDto) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

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
        // 1. get the product from db
        Product product = getProductById(productId);

        // 2. upload image to server
        // 3. get the file name of uploaded image
        String path = "images/";
        String fileName = uploadImage(path, image);

        // 4. updating the new file name to the product
        product.setImage(fileName);

        // 5. save and return dto after mapping product to dto
        return modelMapper.map(productRepository.save(product),
                ProductDTO.class);
    }

    private String uploadImage(String path, MultipartFile image) throws IOException {
        // 1. file names of current / original file
        String originalFilename = image.getOriginalFilename();

        // 2. generate a unique file name
        String randomId = UUID.randomUUID().toString();

        // image.jpg --> 1234 --> 1234.jpg
        assert originalFilename != null;
        String fileName = randomId.concat(originalFilename.substring(originalFilename.lastIndexOf(".")));
        String filePath = path + File.separator + fileName;

        // 3. check if path is existed or create a path
        File folder = new File(path);
        if (!folder.exists()) folder.mkdir();

        // 4. upload to server
        Files.copy(image.getInputStream(), Paths.get(filePath));

        return fileName;
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
        Product product = getProductById(productId);
        productRepository.delete(product);
        return modelMapper.map(product, ProductDTO.class);
    }
}
