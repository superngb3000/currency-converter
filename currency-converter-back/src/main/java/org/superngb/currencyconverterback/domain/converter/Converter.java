package org.superngb.currencyconverterback.domain.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.superngb.currencyconverterback.dto.ConversionRequestModel;
import org.superngb.currencyconverterback.dto.ConversionResponseModel;
import org.superngb.currencyconverterback.dto.CurrencyRateDtoModel;
import org.superngb.currencyconverterback.entity.CurrencyRate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Converter implements IConverter {

    private final ICurrencyRateConverterDataAccess currencyRateConverterDataAccess;

    @Override
    public ResponseEntity<?> getAllCurrencies() {
        List<CurrencyRate> currencyRateList = currencyRateConverterDataAccess.getAll();

        if (currencyRateList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<CurrencyRateDtoModel> currencyRateDtoModelList = CurrencyRateDtoModel.mapper(currencyRateList);
        return ResponseEntity.ok(currencyRateDtoModelList);
    }

    @Override
    public ResponseEntity<?> convertCurrencies(ConversionRequestModel conversionRequestModel) {
        String firstCharCode = conversionRequestModel.firstCurrency().toUpperCase();
        String secondCharCode = conversionRequestModel.secondCurrency().toUpperCase();

        CurrencyRate firstCurrency = currencyRateConverterDataAccess.getByCharCode(firstCharCode);
        CurrencyRate secondCurrency = currencyRateConverterDataAccess.getByCharCode(secondCharCode);

        if (firstCurrency == null || secondCurrency == null) {
            return ResponseEntity.notFound().build();
        }

        BigDecimal firstAmount = conversionRequestModel.firstAmount();
        BigDecimal secondAmount = conversionRequestModel.secondAmount();

        if (firstAmount != null && secondAmount != null) {
            return ResponseEntity.unprocessableEntity().body("Должно быть заполнено только одно поле суммы");
        }

        CurrencyRate fromCurrency;
        CurrencyRate toCurrency;
        BigDecimal amount;
        if (firstAmount != null) {
            fromCurrency = firstCurrency;
            toCurrency = secondCurrency;
            amount = firstAmount;
        } else {
            fromCurrency = secondCurrency;
            toCurrency = firstCurrency;
            amount = secondAmount;
        }

        ConversionResponseModel responseModel;
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            responseModel = new ConversionResponseModel(
                    fromCurrency.getCharCode(),
                    toCurrency.getCharCode(),
                    amount,
                    BigDecimal.ZERO
            );

            return ResponseEntity.ok(responseModel);
        }

        BigDecimal fromRatePerBase = fromCurrency.getValue().divide(BigDecimal.valueOf(fromCurrency.getNominal()), 10, RoundingMode.DOWN);
        BigDecimal toRatePerBase = toCurrency.getValue().divide(BigDecimal.valueOf(toCurrency.getNominal()), 10, RoundingMode.DOWN);

        BigDecimal result = amount.multiply(fromRatePerBase).divide(toRatePerBase, 2, RoundingMode.DOWN);

        responseModel = new ConversionResponseModel(
                fromCurrency.getCharCode(),
                toCurrency.getCharCode(),
                amount,
                result
        );

        return ResponseEntity.ok(responseModel);
    }
}
