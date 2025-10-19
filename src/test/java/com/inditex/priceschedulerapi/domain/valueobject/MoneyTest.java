package com.inditex.priceschedulerapi.domain.valueobject;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {

    /**
     * Test for Money.of(BigDecimal, String) method.
     * Verifies correct creation of a Money object with a valid amount and currency code.
     */
    @Test
    void testOfWithValidAmountAndCurrencyCode() {
        BigDecimal amount = BigDecimal.valueOf(100.50);
        String currencyCode = "USD";

        Money money = Money.of(amount, currencyCode);

        assertNotNull(money);
        assertEquals(amount, money.getAmount());
        assertEquals(currencyCode, money.getCurrencyCode());
    }

    /**
     * Test for Money.of(BigDecimal, String) with null amount.
     * Verifies that IllegalArgumentException is thrown when the amount is null.
     */
    @Test
    void testOfWithNullAmountAndCurrencyCode() {
        String currencyCode = "USD";

        assertThrows(IllegalArgumentException.class, () ->
                Money.of(null, currencyCode), "Amount cannot be null");
    }

    /**
     * Test for Money.of(BigDecimal, String) with negative amount.
     * Verifies that IllegalArgumentException is thrown when the amount is negative.
     */
    @Test
    void testOfWithNegativeAmountAndCurrencyCode() {
        BigDecimal amount = BigDecimal.valueOf(-10.00);
        String currencyCode = "USD";

        assertThrows(IllegalArgumentException.class, () ->
                Money.of(amount, currencyCode), "Amount cannot be negative");
    }

    /**
     * Test for Money.of(BigDecimal, String) with null currency code.
     * Verifies that IllegalArgumentException is thrown when the currency code is null.
     */
    @Test
    void testOfWithNullCurrencyCode() {
        BigDecimal amount = BigDecimal.valueOf(100.00);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                Money.of(amount, (String) null));

        assertEquals("Currency code cannot be null", exception.getMessage());
    }

    /**
     * Test for Money.of(BigDecimal, Currency) method.
     * Verifies correct creation of a Money object with a valid amount and Currency instance.
     */
    @Test
    void testOfWithValidAmountAndCurrencyInstance() {
        BigDecimal amount = BigDecimal.valueOf(50.25);
        Currency currency = Currency.getInstance("EUR");

        Money money = Money.of(amount, currency);

        assertNotNull(money);
        assertEquals(amount, money.getAmount());
        assertEquals(currency, money.getCurrency());
    }

    /**
     * Test for Money.of(BigDecimal, Currency) with null amount.
     * Verifies that IllegalArgumentException is thrown when the amount is null.
     */
    @Test
    void testOfWithNullAmountAndCurrencyInstance() {
        Currency currency = Currency.getInstance("EUR");

        assertThrows(IllegalArgumentException.class, () ->
                Money.of(null, currency), "Amount cannot be null");
    }

    /**
     * Test for Money.of(BigDecimal, Currency) with negative amount.
     * Verifies that IllegalArgumentException is thrown when the amount is negative.
     */
    @Test
    void testOfWithNegativeAmountAndCurrencyInstance() {
        BigDecimal amount = BigDecimal.valueOf(-5.75);
        Currency currency = Currency.getInstance("EUR");

        assertThrows(IllegalArgumentException.class, () ->
                Money.of(amount, currency), "Amount cannot be negative");
    }

    /**
     * Test for Money.of(BigDecimal, Currency) with null currency.
     * Verifies that IllegalArgumentException is thrown when the currency is null.
     */
    @Test
    void testOfWithNullCurrencyInstance() {
        BigDecimal amount = BigDecimal.valueOf(10.00);

        assertThrows(IllegalArgumentException.class, () ->
                Money.of(amount, (Currency) null), "Currency cannot be null");
    }
}