package org.ecommerce.app.service;

import org.ecommerce.app.payload.cart.CartDTO;

public interface CartService {
    CartDTO addProductToCart(Long productId, Integer quantity);
}
