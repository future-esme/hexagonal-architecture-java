package md.rald.esme.adapter.in.rest.cart;

import md.rald.esme.model.cart.Cart;
import md.rald.esme.model.money.Money;

import java.util.List;

public record CartWebModel(
        List<CartLineItemWebModel> lineItems, int numberOfItems, Money subTotal) {

  static CartWebModel fromDomainModel(Cart cart) {
    return new CartWebModel(
        cart.lineItems().stream().map(CartLineItemWebModel::fromDomainModel).toList(),
        cart.numberOfItems(),
        cart.subTotal());
  }
}