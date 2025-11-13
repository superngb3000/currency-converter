package org.superngb.currencyconverterback.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.superngb.currencyconverterback.entity.CurrencyRate;

import java.util.List;

@Schema(description = "Модель данных валюты, возвращаемая при запросе списка валют")
public record CurrencyRateDtoModel(
        @Schema(example = "AUD",  description = "Трехбуквенный код валюты")
        String charCode,

        @Schema(example = "Австралийский доллар",  description = "Полное наименование валюты")
        String name
) {

    public static CurrencyRateDtoModel mapper(CurrencyRate currencyRate) {
        return new CurrencyRateDtoModel(
                currencyRate.getCharCode(),
                currencyRate.getName()
        );
    }

    public static List<CurrencyRateDtoModel> mapper(List<CurrencyRate> currencyRateList) {
        return currencyRateList.stream()
                .map(CurrencyRateDtoModel::mapper)
                .toList();
    }
}
