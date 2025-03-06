package org.ecommerce.app.serviceImpl;

import org.ecommerce.app.exceptions.APIException;
import org.ecommerce.app.exceptions.ResourceNotFoundException;
import org.ecommerce.app.model.Cart;
import org.ecommerce.app.model.Category;
import org.ecommerce.app.model.Product;
import org.ecommerce.app.payload.cart.CartDTO;
import org.ecommerce.app.payload.product.ProductDTO;
import org.ecommerce.app.payload.product.ProductResponse;
import org.ecommerce.app.repository.CartItemRepository;
import org.ecommerce.app.repository.CartRepository;
import org.ecommerce.app.repository.CategoryRepository;
import org.ecommerce.app.repository.ProductRepository;
import org.ecommerce.app.service.CartService;
import org.ecommerce.app.service.FileService;
import org.ecommerce.app.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    @Autowired
    private FileService fileService;
    @Value("${project.images}")
    private String path;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartService cartService;

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
    public ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sort = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> productPage = productRepository.findAll(pageDetails);
        return getProductResponse(productPage);
    }

    private Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
    }

    @Override
    public ProductResponse getAllProductsByCategoryId(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        Sort sort = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> productPage = productRepository.findByCategoryOrderByPriceAsc(category, pageDetails);

        return getProductResponse(productPage);
    }

    @Override
    public ProductResponse getAllProductsByCategoryKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sort = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> productPage =  productRepository.findByProductNameLikeIgnoreCase('%' + keyword + '%', pageDetails);
        return getProductResponse(productPage);
    }

    private ProductResponse getProductResponse(Page<Product> productPage) {
        List<Product> products = productPage.getContent();

        if (products.isEmpty())
            throw new APIException("No products found!");

        List<ProductDTO> productDTOS = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();

        return ProductResponse.builder()
                .content(productDTOS)
                .pageNumber(productPage.getNumber())
                .pageSize(productPage.getSize())
                .totalElements(productPage.getTotalElements())
                .totalPages(productPage.getTotalPages())
                .lastPage(productPage.isLast())
                .build();
    }

    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
        Product savedProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        Product product = modelMapper.map(productDTO, Product.class);

        savedProduct.setProductName(product.getProductName() == null  ? savedProduct.getProductName() : product.getProductName());
        savedProduct.setImage(product.getImage( )== null? savedProduct.getImage() : product.getImage());
        savedProduct.setDescription(product.getDescription() == null ? savedProduct.getDescription() : product.getDescription());
        savedProduct.setQuantity(product.getQuantity() != null ? product.getQuantity() : savedProduct.getQuantity());
        savedProduct.setPrice(product.getPrice() != null ? product.getPrice() : savedProduct.getPrice());
        savedProduct.setDiscount(product.getDiscount() != null ? product.getDiscount() : savedProduct.getDiscount());
        double specialPrice = (savedProduct.getPrice() - ((savedProduct.getDiscount() * 0.01) * savedProduct.getPrice()));
        savedProduct.setSpecialPrice(specialPrice);

        Product finalSavedProduct = productRepository.save(savedProduct);

        List<Cart> carts = cartRepository.findCartsByProductId(productId);

        List<CartDTO> cartDTOs = carts.stream().map(cart -> {
            CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

            List<ProductDTO> products = cart.getCartItems().stream()
                    .map(p -> modelMapper.map(p.getProduct(), ProductDTO.class)).collect(Collectors.toList());

            cartDTO.setProducts(products);

            return cartDTO;

        }).toList();

        cartDTOs.forEach(cart -> cartService.updateProductInCarts(cart.getCartId(), productId));

        return modelMapper.map(finalSavedProduct, ProductDTO.class);
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
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        List<Cart> carts = cartRepository.findCartsByProductId(productId);

        carts.forEach(cart -> cartService.deleteProductFromCart(cart.getCartId(), productId));

        productRepository.delete(product);
        return modelMapper.map(product, ProductDTO.class);
    }
}
