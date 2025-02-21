package org.ecommerce.app.service;

import org.ecommerce.app.payload.cart.CartDTO;

import java.util.List;

public interface CartService {
    CartDTO addProductToCart(Long productId, Integer quantity);

    List<CartDTO> getAllCarts();
}
