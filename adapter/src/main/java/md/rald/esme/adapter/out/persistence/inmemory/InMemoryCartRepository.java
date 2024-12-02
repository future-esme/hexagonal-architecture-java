package md.rald.esme.adapter.out.persistence.inmemory;

import md.rald.esme.model.cart.Cart;
import md.rald.esme.model.customer.CustomerId;
import md.rald.esme.application.port.out.persistence.CartRepository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryCartRepository implements CartRepository {

    private final Map<CustomerId, Cart> carts = new ConcurrentHashMap<>();

    @Override
    public void save(Cart cart) {
        carts.put(cart.getId(), cart);
    }

    @Override
    public Optional<Cart> findByCustomerId(CustomerId customerId) {
        return Optional.ofNullable(carts.get(customerId));
    }

    @Override
    public void deleteById(CustomerId customerId) {
        carts.remove(customerId);
    }
}