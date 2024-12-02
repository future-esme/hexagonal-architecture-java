package md.rald.esme.adapter.in.rest.cart;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import md.rald.esme.model.cart.Cart;
import md.rald.esme.model.cart.NotEnoughItemsInStockException;

import md.rald.esme.application.port.in.cart.AddToCartUseCase;
import md.rald.esme.application.port.in.cart.ProductNotFoundException;
import md.rald.esme.model.customer.CustomerId;
import md.rald.esme.model.product.ProductId;

import static md.rald.esme.adapter.in.rest.common.ControllerCommons.clientErrorException;
import static md.rald.esme.adapter.in.rest.common.CustomerIdParser.parseCustomerId;
import static md.rald.esme.adapter.in.rest.common.ProductIdParser.parseProductId;

@Path("/carts")
@Produces(MediaType.APPLICATION_JSON)
public class AddToCartController {

    private final AddToCartUseCase addToCartUseCase;

    public AddToCartController(AddToCartUseCase addToCartUseCase) {
        this.addToCartUseCase = addToCartUseCase;
    }

    @POST
    @Path("/{customerId}/line-items")
    public CartWebModel addLineItem(
            @PathParam("customerId") String customerIdString,
            @QueryParam("productId") String productIdString,
            @QueryParam("quantity") int quantity) {
        CustomerId customerId = parseCustomerId(customerIdString);
        ProductId productId = parseProductId(productIdString);

        try {
            Cart cart = addToCartUseCase.addToCart(customerId, productId, quantity);
            return CartWebModel.fromDomainModel(cart);
        } catch (ProductNotFoundException e) {
            throw clientErrorException(
                    Response.Status.BAD_REQUEST, "The requested product does not exist");
        } catch (NotEnoughItemsInStockException e) {
            throw clientErrorException(
                    Response.Status.BAD_REQUEST,
                    "Only %d items in stock".formatted(e.itemsInStock()));
        }
    }
}