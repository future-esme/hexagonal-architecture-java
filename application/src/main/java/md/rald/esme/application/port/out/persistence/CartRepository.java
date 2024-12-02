package md.rald.esme.application.port.out.persistence;

import md.rald.esme.model.cart.Cart;
import md.rald.esme.model.customer.CustomerId;

import java.util.Optional;

public interface CartRepository {

    void save(Cart cart);

    Optional<Cart> findByCustomerId(CustomerId customerId);

    void deleteById(CustomerId customerId);
}