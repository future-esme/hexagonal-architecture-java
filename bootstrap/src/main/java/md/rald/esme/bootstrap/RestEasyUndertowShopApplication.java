package md.rald.esme.bootstrap;

import jakarta.ws.rs.core.Application;
import md.rald.esme.adapter.in.rest.cart.AddToCartController;
import md.rald.esme.adapter.in.rest.cart.EmptyCartController;
import md.rald.esme.adapter.in.rest.cart.GetCartController;
import md.rald.esme.adapter.in.rest.product.FindProductsController;
import md.rald.esme.adapter.out.persistence.inmemory.InMemoryCartRepository;
import md.rald.esme.adapter.out.persistence.inmemory.InMemoryProductRepository;
import md.rald.esme.application.port.in.cart.AddToCartUseCase;
import md.rald.esme.application.port.in.cart.EmptyCartUseCase;
import md.rald.esme.application.port.in.cart.GetCartUseCase;
import md.rald.esme.application.port.in.product.FindProductsUseCase;
import md.rald.esme.application.port.out.persistence.CartRepository;
import md.rald.esme.application.port.out.persistence.ProductRepository;
import md.rald.esme.application.service.cart.AddToCartService;
import md.rald.esme.application.service.cart.EmptyCartService;
import md.rald.esme.application.service.cart.GetCartService;
import md.rald.esme.application.service.product.FindProductsService;

import java.util.Set;

public class RestEasyUndertowShopApplication extends Application {
    private CartRepository cartRepository;
    private ProductRepository productRepository;

    @Override
    public Set<Object> getSingletons() {
        initPersistenceAdapters();

        return Set.of(
                addToCartController(),
                getCartController(),
                emptyCartController(),
                findProductsController());
    }

    private void initPersistenceAdapters() {
        cartRepository = new InMemoryCartRepository();
        productRepository = new InMemoryProductRepository();
    }

    private AddToCartController addToCartController() {
        AddToCartUseCase addToCartUseCase =
                new AddToCartService(cartRepository, productRepository);
        return new AddToCartController(addToCartUseCase);
    }

    private GetCartController getCartController() {
        GetCartUseCase getCartUseCase = new GetCartService(cartRepository);
        return new GetCartController(getCartUseCase);
    }

    private EmptyCartController emptyCartController() {
        EmptyCartUseCase emptyCartUseCase = new EmptyCartService(cartRepository);
        return new EmptyCartController(emptyCartUseCase);
    }

    private FindProductsController findProductsController() {
        FindProductsUseCase findProductsUseCase = new FindProductsService(productRepository);
        return new FindProductsController(findProductsUseCase);
    }
}
