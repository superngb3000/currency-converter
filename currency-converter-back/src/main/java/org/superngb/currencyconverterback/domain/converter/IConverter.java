package org.superngb.currencyconverterback.domain.converter;

import org.springframework.http.ResponseEntity;
import org.superngb.currencyconverterback.dto.ConversionRequestModel;

public interface IConverter {
    ResponseEntity<?> getAllCurrencies();

    ResponseEntity<?> convertCurrencies(ConversionRequestModel conversionRequestModel);
}
