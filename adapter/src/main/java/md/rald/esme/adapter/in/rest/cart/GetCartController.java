package md.rald.esme.adapter.in.rest.cart;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import md.rald.esme.adapter.in.rest.common.CustomerIdParser;
import md.rald.esme.model.cart.Cart;
import md.rald.esme.model.customer.CustomerId;
import md.rald.esme.application.port.in.cart.GetCartUseCase;

@Path("/carts")
@Produces(MediaType.APPLICATION_JSON)
public class GetCartController {

    private final GetCartUseCase getCartUseCase;

    public GetCartController(GetCartUseCase getCartUseCase) {
        this.getCartUseCase = getCartUseCase;
    }

    @GET
    @Path("/{customerId}")
    public CartWebModel getCart(@PathParam("customerId") String customerIdString) {
        CustomerId customerId = CustomerIdParser.parseCustomerId(customerIdString);
        Cart cart = getCartUseCase.getCart(customerId);
        return CartWebModel.fromDomainModel(cart);
    }
}