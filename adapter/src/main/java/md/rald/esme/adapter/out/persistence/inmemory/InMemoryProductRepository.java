package md.rald.esme.adapter.out.persistence.inmemory;

import md.rald.esme.adapter.out.persistence.DemoProducts;
import md.rald.esme.application.port.out.persistence.ProductRepository;
import md.rald.esme.model.product.Product;
import md.rald.esme.model.product.ProductId;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryProductRepository implements ProductRepository {

    private final Map<ProductId, Product> products = new ConcurrentHashMap<>();

    public InMemoryProductRepository() {
        createDemoProducts();
    }

    private void createDemoProducts() {
        DemoProducts.DEMO_PRODUCTS.forEach(this::save);
    }

    @Override
    public void save(Product product) {
        products.put(product.getId(), product);
    }

    @Override
    public Optional<Product> findById(ProductId productId) {
        return Optional.ofNullable(products.get(productId));
    }

    @Override
    public List<Product> findByNameOrDescription(String query) {
        String queryLowerCase = query.toLowerCase(Locale.ROOT);
        return products.values().stream()
                .filter(product -> matchesQuery(product, queryLowerCase))
                .toList();
    }

    private boolean matchesQuery(Product product, String query) {
        return product.getName().toLowerCase(Locale.ROOT).contains(query)
                || product.getDescription().toLowerCase(Locale.ROOT).contains(query);
    }
}