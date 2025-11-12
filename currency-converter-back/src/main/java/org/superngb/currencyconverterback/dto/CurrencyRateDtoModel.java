package org.superngb.currencyconverterback.dto;

import org.superngb.currencyconverterback.entity.CurrencyRate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public record CurrencyRateDtoModel(
        String charCode,

        String name,

        Integer nominal,

        BigDecimal value
) {

    public static CurrencyRateDtoModel mapper(CurrencyRate currencyRate) {
        return new CurrencyRateDtoModel(
                currencyRate.getCharCode(),
                currencyRate.getName(),
                currencyRate.getNominal(),
                currencyRate.getValue().setScale(2, RoundingMode.DOWN)
        );
    }

    public static List<CurrencyRateDtoModel> mapper(List<CurrencyRate> currencyRateList) {
        return currencyRateList.stream()
                .map(CurrencyRateDtoModel::mapper)
                .toList();
    }
}
