package org.superngb.currencyconverterback.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.superngb.currencyconverterback.domain.converter.IConverter;
import org.superngb.currencyconverterback.dto.ConversionRequestModel;

@RestController
@RequestMapping("/api/converter")
@RequiredArgsConstructor
public class ConverterController {

    private final IConverter converter;

    @GetMapping
    public ResponseEntity<?> getAllCurrencies() {
        return converter.getAllCurrencies();
    }

    @PostMapping
    public ResponseEntity<?> convertCurrencies(@Valid @RequestBody ConversionRequestModel conversionRequestModel) {
        return converter.convertCurrencies(conversionRequestModel);
    }
}
