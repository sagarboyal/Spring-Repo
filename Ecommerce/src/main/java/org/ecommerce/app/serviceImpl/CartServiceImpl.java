package org.ecommerce.app.serviceImpl;

import org.ecommerce.app.exceptions.APIException;
import org.ecommerce.app.exceptions.ResourceNotFoundException;
import org.ecommerce.app.model.Cart;
import org.ecommerce.app.model.CartItem;
import org.ecommerce.app.model.Product;
import org.ecommerce.app.payload.cart.CartDTO;
import org.ecommerce.app.payload.product.ProductDTO;
import org.ecommerce.app.repository.CartItemRepository;
import org.ecommerce.app.repository.CartRepository;
import org.ecommerce.app.repository.ProductRepository;
import org.ecommerce.app.service.CartService;
import org.ecommerce.app.util.AuthUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AuthUtil authUtil;

    @Override
    public CartDTO addProductToCart(Long productId, Integer quantity) {
        Cart cart = createCart();

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("product", "id", productId));

        CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(productId, cart.getCartId());

        if (cartItem != null)
            throw new APIException("Product "+product.getProductName()+" has already been added to the cart");

        if (product.getQuantity() == 0)
            throw new APIException("Product "+product.getProductName()+" is out of stock");

        if (product.getQuantity() < quantity)
            throw new APIException("Please, make an order of the Product "+product.getProductName()+" less than or equal to the quantity of "+quantity+".");

        CartItem newCartItem = new CartItem();
        newCartItem.setCart(cart);
        newCartItem.setProduct(product);
        newCartItem.setQuantity(quantity);
        newCartItem.setDiscount(product.getDiscount());
        newCartItem.setPrice(product.getPrice());

        cartItemRepository.save(newCartItem);

        product.setQuantity(product.getQuantity());

        double totalPrice = cart.getTotalPrice() + (product.getSpecialPrice() * quantity);
        cart.setTotalPrice(totalPrice);

        cartRepository.save(cart);

        List<CartItem> cartItems = cart.getCartItems();

        Stream<ProductDTO> productStream = cartItems.stream().map(item -> {
            ProductDTO map = modelMapper.map(item.getProduct(), ProductDTO.class);
            map.setQuantity(item.getQuantity());
            return map;
        });

        return CartDTO.builder()
                .cartId(cart.getCartId())
                .totalPrice(cart.getTotalPrice())
                .products(productStream.toList())
                .build();
    }

    private Cart createCart() {
        Cart userCart = cartRepository.findCartByEmail(authUtil.loggedInEmail());
        if (userCart != null)
            return userCart;

        Cart newCart = new Cart();
        newCart.setTotalPrice(0.00);
        newCart.setUser(authUtil.loggedInUser());
        return cartRepository.save(newCart);
    }
}
