package md.rald.esme.adapter.in.rest.cart;

import io.restassured.response.Response;
import jakarta.ws.rs.core.Application;
import md.rald.esme.adapter.in.rest.HttpTestCommons;
import md.rald.esme.application.port.in.cart.AddToCartUseCase;
import md.rald.esme.application.port.in.cart.EmptyCartUseCase;
import md.rald.esme.application.port.in.cart.GetCartUseCase;
import md.rald.esme.application.port.in.cart.ProductNotFoundException;
import md.rald.esme.model.cart.Cart;
import md.rald.esme.model.cart.NotEnoughItemsInStockException;
import md.rald.esme.model.customer.CustomerId;
import md.rald.esme.model.product.Product;
import md.rald.esme.model.product.ProductId;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static io.restassured.RestAssured.given;
import static md.rald.esme.model.money.TestMoneyFactory.euros;
import static md.rald.esme.model.product.TestProductFactory.createTestProduct;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CartsControllerTest {

    private static final CustomerId TEST_CUSTOMER_ID = new CustomerId(61157);
    private static final Product TEST_PRODUCT_1 = createTestProduct(euros(19, 99));

    private static final AddToCartUseCase addToCartUseCase = mock(AddToCartUseCase.class);
    private static final GetCartUseCase getCartUseCase = mock(GetCartUseCase.class);
    private static final EmptyCartUseCase emptyCartUseCase = mock(EmptyCartUseCase.class);

    private static UndertowJaxrsServer server;

    @BeforeAll
    static void init() {
        server =
                new UndertowJaxrsServer()
                        .setPort(HttpTestCommons.TEST_PORT)
                        .start()
                        .deploy(
                                new Application() {
                                    @Override
                                    public Set<Object> getSingletons() {
                                        return Set.of(
                                                new AddToCartController(addToCartUseCase),
                                                new GetCartController(getCartUseCase),
                                                new EmptyCartController(emptyCartUseCase));
                                    }
                                });
    }

    @AfterAll
    static void stop() {
        server.stop();
    }

    @Test
    void givenSomeTestData_addLineItem_invokesAddToCartUseCaseAndReturnsUpdatedCart()
            throws NotEnoughItemsInStockException, ProductNotFoundException {
        // Arrange
        CustomerId customerId = TEST_CUSTOMER_ID;
        ProductId productId = TEST_PRODUCT_1.getId();
        int quantity = 5;

        Cart cart = new Cart(customerId);
        cart.addProduct(TEST_PRODUCT_1, quantity);

        when(addToCartUseCase.addToCart(customerId, productId, quantity)).thenReturn(cart);

        // Act
        Response response =
                given()
                        .port(HttpTestCommons.TEST_PORT)
                        .queryParam("productId", productId.value())
                        .queryParam("quantity", quantity)
                        .post("/carts/" + customerId.value() + "/line-items")
                        .then()
                        .extract()
                        .response();

        // Assert
        CartsControllerAssertions.assertThatResponseIsCart(response, cart);
    }

    // more tests

}