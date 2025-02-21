package org.ecommerce.app.controller;

import org.ecommerce.app.payload.cart.CartDTO;
import org.ecommerce.app.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping()
    public ResponseEntity<List<CartDTO>> getCarts() {
        List<CartDTO> cartDTOS = cartService.getAllCarts();
        return new ResponseEntity<>(cartDTOS, HttpStatus.FOUND);
    }

    @PostMapping("/products/{productId}/quantity/{quantity}")
    public ResponseEntity<CartDTO> saveProductHandler(@PathVariable Long productId,
                                                      @PathVariable Integer quantity) {
        CartDTO data = cartService.addProductToCart(productId, quantity);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(data);
    }
}
