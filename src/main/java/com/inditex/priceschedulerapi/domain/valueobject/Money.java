package com.inditex.priceschedulerapi.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * Value Object representing monetary value with currency.
 * Immutable and self-validating.
 */
@Getter
@EqualsAndHashCode
public final class Money {

    private final BigDecimal amount;
    private final Currency currency;

    private Money(BigDecimal amount, Currency currency) {
        validateAmount(amount);
        validateCurrency(currency);
        this.amount = amount;
        this.currency = currency;
    }

    public static Money of(BigDecimal amount, String currencyCode) {
        if (currencyCode == null) {
            throw new IllegalArgumentException("Currency code cannot be null");
        }
        Currency currency = Currency.getInstance(currencyCode);
        return new Money(amount, currency);
    }

    public static Money of(BigDecimal amount, Currency currency) {
        return new Money(amount, currency);
    }

    private void validateAmount(BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
    }

    private void validateCurrency(Currency currency) {
        if (currency == null) {
            throw new IllegalArgumentException("Currency cannot be null");
        }
    }

    public String getCurrencyCode() {
        return currency.getCurrencyCode();
    }

    @Override
    public String toString() {
        return amount + " " + currency.getCurrencyCode();
    }
}
