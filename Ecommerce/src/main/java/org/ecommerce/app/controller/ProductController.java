package org.ecommerce.app.controller;

import jakarta.validation.Valid;
import org.ecommerce.app.config.AppConstants;
import org.ecommerce.app.payload.product.ProductDTO;
import org.ecommerce.app.payload.product.ProductResponse;
import org.ecommerce.app.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProducts(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY_PRODUCT, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder
    ) {
        return ResponseEntity.status(HttpStatus.FOUND).body(productService.getAllProducts(pageNumber, pageSize, sortBy, sortOrder));
    }

    @GetMapping("/public/categories/{categoryId}/products")
    public ResponseEntity<ProductResponse> getAllProductsByCategory(
            @PathVariable Long categoryId,
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY_PRODUCT, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder
    ) {
        return ResponseEntity.status(HttpStatus.FOUND).body(productService.getAllProductsByCategoryId(categoryId, pageNumber, pageSize, sortBy, sortOrder));
    }

    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse> getAllProductsByKeyWord(
            @PathVariable String keyword,
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY_PRODUCT, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {
        return ResponseEntity.status(HttpStatus.FOUND).body(productService.getAllProductsByCategoryKeyword(keyword,pageNumber, pageSize, sortBy, sortOrder ));
    }

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> addProduct(@Valid @RequestBody ProductDTO productDto,
                                                 @PathVariable Long categoryId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.addProduct(categoryId, productDto));
    }
    @PutMapping("/admin/products")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDto) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.updateProduct(productDto));
    }
    @PutMapping("/admin/products/{productId}/image")
    public ResponseEntity<ProductDTO> updateProductImage(@Valid @PathVariable Long productId,
                                                         @RequestParam("image") MultipartFile image) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(productService.updateProductImage(productId, image));
    }
    @DeleteMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.deleteProduct(productId));
    }
}
