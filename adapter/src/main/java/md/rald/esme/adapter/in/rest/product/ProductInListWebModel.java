package md.rald.esme.adapter.in.rest.product;

import md.rald.esme.model.money.Money;
import md.rald.esme.model.product.Product;

public record ProductInListWebModel(
        String id, String name, Money price, int itemsInStock) {

    public static ProductInListWebModel fromDomainModel(Product product) {
        return new ProductInListWebModel(
                product.getId().value(), product.getName(), product.getPrice(), product.getItemsInStock());
    }
}