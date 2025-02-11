package org.ecommerce.app.service;

import org.ecommerce.app.model.Category;
import org.ecommerce.app.model.Product;
import org.ecommerce.app.payload.product.ProductDTO;
import org.ecommerce.app.payload.product.ProductResponse;

public interface ProductService {
    ProductDTO addProduct(Long categoryId, Product product);

    ProductResponse getAllProducts();

    ProductResponse getAllProductsByCategoryId(Long categoryId);
}
