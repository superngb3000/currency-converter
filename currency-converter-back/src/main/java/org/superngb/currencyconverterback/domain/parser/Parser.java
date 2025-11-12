package org.superngb.currencyconverterback.domain.parser;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.superngb.currencyconverterback.client.CbClient;
import org.superngb.currencyconverterback.config.BaseCurrencyConfig;
import org.superngb.currencyconverterback.dto.CbResponseModel;
import org.superngb.currencyconverterback.entity.CurrencyRate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Parser implements IParser {

    private final ICurrencyRateParserDataAccess currencyRateParserDataAccess;
    private final CbClient cbClient;
    private final BaseCurrencyConfig baseCurrencyConfig;

    @Override
    public void restoreCurrencyRates() {
        CbResponseModel cbResponseModel = cbClient.getCurrencyRates();

        currencyRateParserDataAccess.deleteAll();

        List<CurrencyRate> currencyRateList = cbResponseModel.valute().values().stream()
                .map(dto -> CurrencyRate.builder()
                        .charCode(dto.charCode())
                        .name(dto.name())
                        .nominal(dto.nominal())
                        .value(dto.value())
                        .build())
                .toList();

        CurrencyRate baseCurrency = CurrencyRate.builder()
                .charCode(baseCurrencyConfig.getCharCode())
                .name(baseCurrencyConfig.getName())
                .nominal(1)
                .value(BigDecimal.ONE)
                .build();

        List<CurrencyRate> toSave = new ArrayList<>(currencyRateList);
        toSave.add(baseCurrency);

        currencyRateParserDataAccess.saveAll(toSave);
    }
}
