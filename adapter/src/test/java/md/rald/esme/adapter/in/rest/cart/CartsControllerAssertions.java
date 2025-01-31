package md.rald.esme.adapter.in.rest.cart;

import static jakarta.ws.rs.core.Response.Status.OK;
import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import md.rald.esme.model.cart.Cart;
import md.rald.esme.model.cart.CartLineItem;

public final class CartsControllerAssertions {

    private CartsControllerAssertions() {
    }

    public static void assertThatResponseIsCart(Response response, Cart cart) {
        assertThat(response.statusCode()).isEqualTo(OK.getStatusCode());

        JsonPath json = response.jsonPath();

        for (int i = 0; i < cart.lineItems().size(); i++) {
            CartLineItem lineItem = cart.lineItems().get(i);

            String lineItemPrefix = "lineItems[%d].".formatted(i);

            assertThat(json.getString(lineItemPrefix + "productId"))
                    .isEqualTo(lineItem.getProduct().getId().value());
            assertThat(json.getString(lineItemPrefix + "productName"))
                    .isEqualTo(lineItem.getProduct().getName());
            assertThat(json.getString(lineItemPrefix + "price.currency"))
                    .isEqualTo(lineItem.getProduct().getPrice().currency().getCurrencyCode());
            assertThat(json.getDouble(lineItemPrefix + "price.amount"))
                    .isEqualTo(lineItem.getProduct().getPrice().amount().doubleValue());
            assertThat(json.getInt(lineItemPrefix + "quantity")).isEqualTo(lineItem.getQuantity());
        }

        assertThat(json.getInt("numberOfItems")).isEqualTo(cart.numberOfItems());

        if (cart.subTotal() != null) {
            assertThat(json.getString("subTotal.currency"))
                    .isEqualTo(cart.subTotal().currency().getCurrencyCode());
            assertThat(json.getDouble("subTotal.amount"))
                    .isEqualTo(cart.subTotal().amount().doubleValue());
        } else {
            assertThat(json.getString("subTotal")).isNull();
        }
    }
}