package md.rald.esme.application.port.in.cart;

import md.rald.esme.model.customer.CustomerId;

public interface EmptyCartUseCase {

    void emptyCart(CustomerId customerId);
}