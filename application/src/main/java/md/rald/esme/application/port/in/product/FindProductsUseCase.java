package md.rald.esme.application.port.in.product;

import md.rald.esme.model.product.Product;

import java.util.List;

public interface FindProductsUseCase {

    List<Product> findByNameOrDescription(String query);
}