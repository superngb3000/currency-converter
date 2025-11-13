package org.superngb.currencyconverterback.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Результат конвертации валют")
public record ConversionResponseModel(
        @Schema(example = "AUD", description = "Конвертируемая валюта (трехбуквенный код)")
        String fromCurrency,

        @Schema(example = "AZN", description = "Целевая валюта (трехбуквенный код)")
        String toCurrency,

        @Schema(example = "10.00", description = "Сумма в исходной валюте")
        BigDecimal fromAmount,

        @Schema(example = "11.08", description = "Результат конвертации")
        BigDecimal toAmount
) {
}
