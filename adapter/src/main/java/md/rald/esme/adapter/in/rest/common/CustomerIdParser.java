package md.rald.esme.adapter.in.rest.common;

import jakarta.ws.rs.core.Response;
import md.rald.esme.model.customer.CustomerId;

import static md.rald.esme.adapter.in.rest.common.ControllerCommons.clientErrorException;

public final class CustomerIdParser {

    private CustomerIdParser() {
    }

    public static CustomerId parseCustomerId(String string) {
        try {
            return new CustomerId(Integer.parseInt(string));
        } catch (IllegalArgumentException e) {
            throw clientErrorException(Response.Status.BAD_REQUEST, "Invalid 'customerId'");
        }
    }
}