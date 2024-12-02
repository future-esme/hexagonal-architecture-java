package md.rald.esme.application.port.in.cart;

import md.rald.esme.model.cart.Cart;
import md.rald.esme.model.customer.CustomerId;

public interface GetCartUseCase {

    Cart getCart(CustomerId customerId);
}