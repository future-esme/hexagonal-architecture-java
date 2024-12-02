package md.rald.esme.model.product;

import md.rald.esme.model.money.Money;

public class Product {

    private final ProductId id;
    private String name;
    private String description;
    private Money price;
    private int itemsInStock;

    public Product(ProductId id, String name, String description, Money price, int itemsInStock) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.itemsInStock = itemsInStock;
    }

    public ProductId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Money getPrice() {
        return price;
    }

    public void setPrice(Money price) {
        this.price = price;
    }

    public int getItemsInStock() {
        return itemsInStock;
    }

    public void setItemsInStock(int itemsInStock) {
        this.itemsInStock = itemsInStock;
    }
}