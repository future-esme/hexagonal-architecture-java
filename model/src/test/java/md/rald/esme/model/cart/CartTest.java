package md.rald.esme.model.cart;

import md.rald.esme.model.product.Product;
import md.rald.esme.model.product.TestProductFactory;
import org.junit.jupiter.api.Test;

import static md.rald.esme.model.money.TestMoneyFactory.euros;
import static org.assertj.core.api.Assertions.assertThat;

class CartTest {

    @Test
    void givenEmptyCart_addTwoProducts_numberOfItemsAndSubTotalIsCalculatedCorrectly()
            throws NotEnoughItemsInStockException {
        Cart cart = TestCartFactory.emptyCartForRandomCustomer();

        Product product1 = TestProductFactory.createTestProduct(euros(12, 99));
        Product product2 = TestProductFactory.createTestProduct(euros(5, 97));

        cart.addProduct(product1, 3);
        cart.addProduct(product2, 5);

        assertThat(cart.numberOfItems()).isEqualTo(8);
        assertThat(cart.subTotal()).isEqualTo(euros(68, 82));
    }

    // more tests

}