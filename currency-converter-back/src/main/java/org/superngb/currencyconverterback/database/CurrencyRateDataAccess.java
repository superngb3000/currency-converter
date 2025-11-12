package org.superngb.currencyconverterback.database;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.superngb.currencyconverterback.domain.parser.ICurrencyRateParserDataAccess;
import org.superngb.currencyconverterback.entity.CurrencyRate;
import org.superngb.currencyconverterback.repository.CurrencyRateRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CurrencyRateDataAccess implements ICurrencyRateParserDataAccess {

    private final CurrencyRateRepository currencyRateRepository;

    @Override
    public void saveAll(List<CurrencyRate> currencyRateList) {
        currencyRateRepository.saveAll(currencyRateList);
    }

    @Override
    public void deleteAll() {
        currencyRateRepository.deleteAll();
    }
}
