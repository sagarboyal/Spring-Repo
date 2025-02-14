package org.ecommerce.app.service;

import org.ecommerce.app.payload.product.ProductDTO;
import org.ecommerce.app.payload.product.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {
    ProductDTO addProduct(Long categoryId, ProductDTO productDto);
    ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    ProductResponse getAllProductsByCategoryId(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    ProductResponse getAllProductsByCategoryKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    ProductDTO updateProduct(ProductDTO productDto);
    ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException;
    ProductDTO deleteProduct(Long productId);
}
