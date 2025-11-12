package org.superngb.currencyconverterback.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public record CbResponseModel(
        @JsonProperty("Date")
        String date,
        @JsonProperty("PreviousDate")
        String previousDate,
        @JsonProperty("PreviousURL")
        String previousUrl,
        @JsonProperty("Timestamp")
        String timestamp,
        @JsonProperty("Valute")
        Map<String, CbCurrencyDtoModel> valute
) {
}
