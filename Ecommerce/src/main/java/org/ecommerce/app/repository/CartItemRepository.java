package org.ecommerce.app.repository;

import org.ecommerce.app.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @Query("SELECT ci FROM CartItem ci WHERE  ci.cart.cartId = ?2 AND ci.product.productId= ?1")
    CartItem findCartItemByProductIdAndCartId(Long productId, Long cartId);
}
