package org.ecommerce.app.service;

import org.ecommerce.app.model.Category;
import org.ecommerce.app.model.Product;
import org.ecommerce.app.payload.product.ProductDTO;
import org.ecommerce.app.payload.product.ProductResponse;

public interface ProductService {
    ProductDTO addProduct(Long categoryId, Product product);
    ProductResponse getAllProducts();
    Product getProductById(Long productId);
    ProductResponse getAllProductsByCategoryId(Long categoryId);
    ProductResponse getAllProductsByCategoryKeyword(String keyword);
    ProductDTO updateProduct(Product product);
    ProductDTO deleteProduct(Long productId);
}
