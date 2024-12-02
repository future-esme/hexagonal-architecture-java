package md.rald.esme.model.cart;

import md.rald.esme.model.money.Money;
import md.rald.esme.model.product.Product;
import md.rald.esme.model.customer.CustomerId;
import md.rald.esme.model.product.ProductId;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Cart {

    private final CustomerId id;

    public Cart(CustomerId id) {
        this.id = id;
    }

    private final Map<ProductId, CartLineItem> lineItems = new LinkedHashMap<>();

    public void addProduct(Product product, int quantity)
            throws NotEnoughItemsInStockException {
        lineItems
                .computeIfAbsent(product.getId(), ignored -> new CartLineItem(product))
                .increaseQuantityBy(quantity, product.getItemsInStock());
    }

    public List<CartLineItem> lineItems() {
        return List.copyOf(lineItems.values());
    }

    public int numberOfItems() {
        return lineItems.values().stream().mapToInt(CartLineItem::getQuantity).sum();
    }

    public Money subTotal() {
        return lineItems.values().stream()
                .map(CartLineItem::subTotal)
                .reduce(Money::add)
                .orElse(null);
    }

    public CustomerId getId() {
        return id;
    }
}