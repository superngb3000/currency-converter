package org.superngb.currencyconverterback.domain.converter;

import org.superngb.currencyconverterback.entity.CurrencyRate;

import java.util.List;

public interface ICurrencyRateConverterDataAccess {
    List<CurrencyRate> getAll();

    CurrencyRate getByCharCode(String charCode);
}
