package org.ecommerce.app.controller;

import org.ecommerce.app.model.Product;
import org.ecommerce.app.payload.product.ProductDTO;
import org.ecommerce.app.payload.product.ProductResponse;
import org.ecommerce.app.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProducts() {
        return ResponseEntity.status(HttpStatus.FOUND).body(productService.getAllProducts());
    }

    @GetMapping("/public/categories/{categoryId}/products")
    public ResponseEntity<ProductResponse> getAllProductsByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.status(HttpStatus.FOUND).body(productService.getAllProductsByCategoryId(categoryId));
    }

    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse> getAllProductsByKeyWord(@PathVariable String keyword) {
        return ResponseEntity.status(HttpStatus.FOUND).body(productService.getAllProductsByCategoryKeyword(keyword));
    }

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody Product product,@PathVariable Long categoryId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.addProduct(categoryId, product));
    }
}
