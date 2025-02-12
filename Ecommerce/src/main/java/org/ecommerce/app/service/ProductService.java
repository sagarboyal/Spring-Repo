package org.ecommerce.app.service;

import org.ecommerce.app.model.Product;
import org.ecommerce.app.payload.product.ProductDTO;
import org.ecommerce.app.payload.product.ProductResponse;

public interface ProductService {
    ProductDTO addProduct(Long categoryId, ProductDTO productDto);
    ProductResponse getAllProducts();
    Product getProductById(Long productId);
    ProductResponse getAllProductsByCategoryId(Long categoryId);
    ProductResponse getAllProductsByCategoryKeyword(String keyword);
    ProductDTO updateProduct(ProductDTO productDto);
    ProductDTO deleteProduct(Long productId);
}
