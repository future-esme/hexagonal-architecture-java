package md.rald.esme.adapter.in.rest.cart;

import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import md.rald.esme.model.customer.CustomerId;
import md.rald.esme.application.port.in.cart.EmptyCartUseCase;

import static md.rald.esme.adapter.in.rest.common.CustomerIdParser.parseCustomerId;

@Path("/carts")
@Produces(MediaType.APPLICATION_JSON)
public class EmptyCartController {

    private final EmptyCartUseCase emptyCartUseCase;

    public EmptyCartController(EmptyCartUseCase emptyCartUseCase) {
        this.emptyCartUseCase = emptyCartUseCase;
    }

    @DELETE
    @Path("/{customerId}")
    public void deleteCart(@PathParam("customerId") String customerIdString) {
        CustomerId customerId = parseCustomerId(customerIdString);
        emptyCartUseCase.emptyCart(customerId);
    }
}