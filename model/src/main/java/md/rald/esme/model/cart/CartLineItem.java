package md.rald.esme.model.cart;

import md.rald.esme.model.money.Money;
import md.rald.esme.model.product.Product;

public class CartLineItem {

  private final Product product;
  private int quantity;

  public CartLineItem(Product product) {
    this.product = product;
  }

  public CartLineItem(Product product, int quantity) {
    this.product = product;
    this.quantity = quantity;
  }

  public void increaseQuantityBy(int augend, int itemsInStock)
      throws NotEnoughItemsInStockException {
    if (augend < 1) {
      throw new IllegalArgumentException("You must add at least one item");
    }

    int newQuantity = quantity + augend;
    if (itemsInStock < newQuantity) {
      throw new NotEnoughItemsInStockException(
          ("Product %s has less items in stock (%d) "
                  + "than the requested total quantity (%d)")
              .formatted(product.getId(), product.getItemsInStock(), newQuantity),
          product.getItemsInStock());
    }

    this.quantity = newQuantity;
  }

  public Money subTotal() {
    return product.getPrice().multiply(quantity);
  }

  public Product getProduct() {
    return product;
  }

  public int getQuantity() {
    return quantity;
  }
}