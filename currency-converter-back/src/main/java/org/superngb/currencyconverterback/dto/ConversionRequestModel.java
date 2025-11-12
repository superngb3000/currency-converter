package org.superngb.currencyconverterback.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

public record ConversionRequestModel(
        @NotNull
        @Pattern(regexp = "^[a-zA-Z]{3}$")
        String firstCurrency,

        @NotNull
        @Pattern(regexp = "^[a-zA-Z]{3}$")
        String secondCurrency,

        @Min(0)
        BigDecimal firstAmount,

        @Min(0)
        BigDecimal secondAmount
) {
}