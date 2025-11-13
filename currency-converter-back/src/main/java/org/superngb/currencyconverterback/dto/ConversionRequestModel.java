package org.superngb.currencyconverterback.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

@Schema(description = "Запрос на конвертацию валют")
public record ConversionRequestModel(
        @Schema(example = "AUD", description = "Первая валюта (трехбуквенный код)")
        @NotNull
        @Pattern(regexp = "^[a-zA-Z]{3}$")
        String firstCurrency,

        @Schema(example = "AZN", description = "Вторая валюта (трехбуквенный код)")
        @NotNull
        @Pattern(regexp = "^[a-zA-Z]{3}$")
        String secondCurrency,

        @Schema(example = "10.00", description = "Сумма в первой валюте (заполните только одно из двух полей)")
        @Min(0)
        BigDecimal firstAmount,

        @Schema(example = "11.08", description = "Сумма во второй валюте (заполните только одно из двух полей)")
        @Min(0)
        BigDecimal secondAmount
) {
}