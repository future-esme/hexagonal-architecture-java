package md.rald.esme.adapter.in.rest.cart;

import md.rald.esme.model.cart.CartLineItem;
import md.rald.esme.model.money.Money;
import md.rald.esme.model.product.Product;

public record CartLineItemWebModel(
        String productId, String productName, Money price, int quantity) {

    public static CartLineItemWebModel fromDomainModel(CartLineItem lineItem) {
        Product product = lineItem.getProduct();
        return new CartLineItemWebModel(
                product.getId().value(), product.getName(), product.getPrice(), lineItem.getQuantity());
    }
}