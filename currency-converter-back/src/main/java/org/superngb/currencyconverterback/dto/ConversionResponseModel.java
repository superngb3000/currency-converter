package org.superngb.currencyconverterback.dto;

import java.math.BigDecimal;

public record ConversionResponseModel(
        String fromCurrency,

        String toCurrency,

        BigDecimal fromAmount,

        BigDecimal toAmount
) {
}
