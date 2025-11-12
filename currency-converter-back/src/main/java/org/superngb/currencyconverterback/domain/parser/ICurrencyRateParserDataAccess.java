package org.superngb.currencyconverterback.domain.parser;

import org.superngb.currencyconverterback.entity.CurrencyRate;

import java.util.List;

public interface ICurrencyRateParserDataAccess {
    void saveAll(List<CurrencyRate> currencyRateList);

    void deleteAll();
}
