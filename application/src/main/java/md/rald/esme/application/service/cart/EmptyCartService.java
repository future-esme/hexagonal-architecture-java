package md.rald.esme.application.service.cart;

import md.rald.esme.model.customer.CustomerId;
import md.rald.esme.application.port.in.cart.EmptyCartUseCase;
import md.rald.esme.application.port.out.persistence.CartRepository;

import java.util.Objects;

public class EmptyCartService implements EmptyCartUseCase {

  private final CartRepository cartRepository;

  public EmptyCartService(CartRepository cartRepository) {
    this.cartRepository = cartRepository;
  }

  @Override
  public void emptyCart(CustomerId customerId) {
    Objects.requireNonNull(customerId, "'customerId' must not be null");

    cartRepository.deleteById(customerId);
  }
}