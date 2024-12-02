package md.rald.esmeesme.application.service.cart;

import md.rald.esme.application.service.cart.EmptyCartService;
import md.rald.esme.model.customer.CustomerId;
import md.rald.esme.application.port.out.persistence.CartRepository;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class EmptyCartServiceTest {

    private static final CustomerId TEST_CUSTOMER_ID = new CustomerId(61157);

    private final CartRepository cartRepository = mock(CartRepository.class);
    private final EmptyCartService emptyCartService = new EmptyCartService(cartRepository);

    @Test
    void emptyCart_invokesDeleteOnThePersistencePort() {
        emptyCartService.emptyCart(TEST_CUSTOMER_ID);

        verify(cartRepository).deleteById(TEST_CUSTOMER_ID);
    }
}