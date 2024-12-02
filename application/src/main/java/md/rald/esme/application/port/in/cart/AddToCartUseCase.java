package md.rald.esme.application.port.in.cart;

import md.rald.esme.model.cart.Cart;
import md.rald.esme.model.cart.NotEnoughItemsInStockException;
import md.rald.esme.model.customer.CustomerId;
import md.rald.esme.model.product.ProductId;

public interface AddToCartUseCase {

    Cart addToCart(CustomerId customerId, ProductId productId, int quantity)
            throws ProductNotFoundException, NotEnoughItemsInStockException;
}