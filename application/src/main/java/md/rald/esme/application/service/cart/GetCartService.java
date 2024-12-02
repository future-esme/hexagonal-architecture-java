package md.rald.esme.application.service.cart;

import md.rald.esme.application.port.in.cart.GetCartUseCase;
import md.rald.esme.application.port.out.persistence.CartRepository;
import md.rald.esme.model.cart.Cart;
import md.rald.esme.model.customer.CustomerId;

import java.util.Objects;

public class GetCartService implements GetCartUseCase {

    private final CartRepository cartRepository;

    public GetCartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public Cart getCart(CustomerId customerId) {
        Objects.requireNonNull(customerId, "'customerId' must not be null");

        return cartRepository
                .findByCustomerId(customerId)
                .orElseGet(() -> new Cart(customerId));
    }
}