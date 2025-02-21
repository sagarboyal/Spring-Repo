package org.ecommerce.app.controller;

import org.ecommerce.app.payload.cart.CartDTO;
import org.ecommerce.app.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/products/{productId}/quantity/{quantity}")
    public ResponseEntity<CartDTO> saveProductHandler(@PathVariable Long productId,
                                                      @PathVariable Integer quantity) {
        CartDTO data = cartService.addProductToCart(productId, quantity);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(data);
    }
}
