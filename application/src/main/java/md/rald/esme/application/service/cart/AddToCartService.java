package md.rald.esme.application.service.cart;

import md.rald.esme.application.port.in.cart.ProductNotFoundException;
import md.rald.esme.model.cart.Cart;
import md.rald.esme.model.cart.NotEnoughItemsInStockException;
import md.rald.esme.model.customer.CustomerId;
import md.rald.esme.application.port.in.cart.AddToCartUseCase;
import md.rald.esme.application.port.out.persistence.CartRepository;
import md.rald.esme.application.port.out.persistence.ProductRepository;
import md.rald.esme.model.product.Product;
import md.rald.esme.model.product.ProductId;

import java.util.Objects;

public class AddToCartService implements AddToCartUseCase {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public AddToCartService(
            CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Cart addToCart(CustomerId customerId, ProductId productId, int quantity)
            throws ProductNotFoundException, NotEnoughItemsInStockException {
        Objects.requireNonNull(customerId, "'customerId' must not be null");
        Objects.requireNonNull(productId, "'productId' must not be null");
        if (quantity < 1) {
            throw new IllegalArgumentException("'quantity' must be greater than 0");
        }

        Product product =
                productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);

        Cart cart =
                cartRepository
                        .findByCustomerId(customerId)
                        .orElseGet(() -> new Cart(customerId));

        cart.addProduct(product, quantity);

        cartRepository.save(cart);

        return cart;
    }
}