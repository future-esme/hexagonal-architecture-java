package md.rald.esme.adapter.in.rest.product;

import io.restassured.response.Response;
import jakarta.ws.rs.core.Application;
import md.rald.esme.adapter.in.rest.HttpTestCommons;
import md.rald.esme.application.port.in.product.FindProductsUseCase;
import md.rald.esme.model.product.Product;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static jakarta.ws.rs.core.Response.Status.BAD_REQUEST;
import static md.rald.esme.adapter.in.rest.product.ProductsControllerAssertions.assertThatResponseIsProductList;
import static md.rald.esme.model.money.TestMoneyFactory.euros;
import static md.rald.esme.model.product.TestProductFactory.createTestProduct;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class ProductsControllerTest {

    private static final Product TEST_PRODUCT_1 = createTestProduct(euros(19, 99));
    private static final Product TEST_PRODUCT_2 = createTestProduct(euros(25, 99));

    private static final FindProductsUseCase findProductsUseCase = mock(FindProductsUseCase.class);

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
                                        return Set.of(new FindProductsController(findProductsUseCase));
                                    }
                                });
    }

    @AfterAll
    static void stop() {
        server.stop();
    }

    @BeforeEach
    void resetMocks() {
        Mockito.reset(findProductsUseCase);
    }

    @Test
    void givenAQueryAndAListOfProducts_findProducts_requestsProductsViaQueryAndReturnsThem() {
        String query = "foo";
        List<Product> productList = List.of(TEST_PRODUCT_1, TEST_PRODUCT_2);

        when(findProductsUseCase.findByNameOrDescription(query)).thenReturn(productList);

        Response response =
                given()
                        .port(HttpTestCommons.TEST_PORT)
                        .queryParam("query", query)
                        .get("/products")
                        .then()
                        .extract()
                        .response();

        assertThatResponseIsProductList(response, productList);
    }

    @Test
    void givenANullQuery_findProducts_returnsError() {
        Response response = given().port(HttpTestCommons.TEST_PORT).get("/products").then().extract().response();

        HttpTestCommons.assertThatResponseIsError(response, BAD_REQUEST, "Missing 'query'");
    }

    @Test
    void givenATooShortQuery_findProducts_returnsError() {
        String query = "e";
        when(findProductsUseCase.findByNameOrDescription(query))
                .thenThrow(IllegalArgumentException.class);

        Response response =
                given()
                        .port(HttpTestCommons.TEST_PORT)
                        .queryParam("query", query)
                        .get("/products")
                        .then()
                        .extract()
                        .response();

        HttpTestCommons.assertThatResponseIsError(response, BAD_REQUEST, "Invalid 'query'");
    }
}