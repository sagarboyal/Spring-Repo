package org.ecommerce.app.controller;

import org.ecommerce.app.model.Cart;
import org.ecommerce.app.payload.cart.CartDTO;
import org.ecommerce.app.service.CartService;
import org.ecommerce.app.util.AuthUtil;
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
    @Autowired
    private AuthUtil authUtil;

    @GetMapping()
    public ResponseEntity<List<CartDTO>> getCartsHandler() {
        List<CartDTO> cartDTOS = cartService.getAllCarts();
        return new ResponseEntity<>(cartDTOS, HttpStatus.FOUND);
    }

    @GetMapping("/users/cart")
    public ResponseEntity<CartDTO> getUserCartsHandler() {
        String email = authUtil.loggedInEmail();

        Long cartId = cartService.findUserCartId(email);

        CartDTO cartDTO = cartService.getUserCart(email, cartId);
        return new ResponseEntity<>(cartDTO, HttpStatus.FOUND);
    }

    @PostMapping("/products/{productId}/quantity/{quantity}")
    public ResponseEntity<CartDTO> saveProductHandler(@PathVariable Long productId,
                                                      @PathVariable Integer quantity) {
        CartDTO data = cartService.addProductToCart(productId, quantity);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(data);
    }

    @PutMapping("/products/{productId}/quantity/{operation}")
    public ResponseEntity<CartDTO> updateProductHandler(@PathVariable Long productId,
                                                        @PathVariable String operation) {
        CartDTO cartDTO = cartService.updateProductQuantityInCart(productId,
                operation.equalsIgnoreCase("delete") ? -1 : 1);
        return ResponseEntity.status(HttpStatus.OK).body(cartDTO);
    }

    @DeleteMapping("/{cartId}/product/{productId}")
    public ResponseEntity<String> deleteProductHandler(@PathVariable Long cartId,
                                                       @PathVariable Long productId) {
        String status = cartService.deleteProductFromCart(cartId, productId);
        return ResponseEntity.status(HttpStatus.OK).body(status);
    }
}
