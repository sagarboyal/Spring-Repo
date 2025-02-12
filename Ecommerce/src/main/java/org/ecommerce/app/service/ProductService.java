package org.ecommerce.app.service;

import org.ecommerce.app.model.Product;
import org.ecommerce.app.payload.product.ProductDTO;
import org.ecommerce.app.payload.product.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {
    ProductDTO addProduct(Long categoryId, ProductDTO productDto);
    ProductResponse getAllProducts();
    Product getProductById(Long productId);
    ProductResponse getAllProductsByCategoryId(Long categoryId);
    ProductResponse getAllProductsByCategoryKeyword(String keyword);
    ProductDTO updateProduct(ProductDTO productDto);
    ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException;
    ProductDTO deleteProduct(Long productId);
}
