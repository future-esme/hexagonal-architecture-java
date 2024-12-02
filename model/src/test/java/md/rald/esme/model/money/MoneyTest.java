package md.rald.esme.model.money;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Currency;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class MoneyTest {

    private static final Currency EUR = Currency.getInstance("EUR");

    @Test
    void givenAmountWithAnInvalidScale_newMoney_throwsIllegalArgumentException() {
        BigDecimal amountWithScale3 = new BigDecimal(BigInteger.valueOf(12999), 3);

        ThrowingCallable invocation = () -> new Money(EUR, amountWithScale3);

        assertThatIllegalArgumentException().isThrownBy(invocation);
    }

    @Test
    void givenAEuroAmount_addADollarAmount_throwsIllegalArgumentException() {
        Money euros = TestMoneyFactory.euros(11, 99);
        Money dollars = TestMoneyFactory.usDollars(11, 99);

        ThrowingCallable invocation = () -> euros.add(dollars);

        assertThatIllegalArgumentException().isThrownBy(invocation);
    }
}