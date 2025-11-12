package org.superngb.currencyconverterback.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record CbCurrencyDtoModel(
        @JsonProperty("ID")
        String id,

        @JsonProperty("NumCode")
        String numCode,

        @JsonProperty("CharCode")
        String charCode,

        @JsonProperty("Nominal")
        Integer nominal,

        @JsonProperty("Name")
        String name,

        @JsonProperty("Value")
        BigDecimal value,

        @JsonProperty("Previous")
        BigDecimal previous
) {
}
