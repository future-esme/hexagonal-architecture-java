package md.rald.esme.application.port.out.persistence;

import md.rald.esme.model.product.Product;
import md.rald.esme.model.product.ProductId;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    void save(Product product);

    Optional<Product> findById(ProductId productId);

    List<Product> findByNameOrDescription(String query);
}