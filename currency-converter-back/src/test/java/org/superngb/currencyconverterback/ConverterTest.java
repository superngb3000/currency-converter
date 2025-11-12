package org.superngb.currencyconverterback;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.superngb.currencyconverterback.domain.converter.Converter;
import org.superngb.currencyconverterback.domain.converter.IConverter;
import org.superngb.currencyconverterback.domain.converter.ICurrencyRateConverterDataAccess;
import org.superngb.currencyconverterback.dto.ConversionRequestModel;
import org.superngb.currencyconverterback.dto.ConversionResponseModel;
import org.superngb.currencyconverterback.dto.CurrencyRateDtoModel;
import org.superngb.currencyconverterback.entity.CurrencyRate;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ConverterTest {

    private ICurrencyRateConverterDataAccess dataAccess;
    private IConverter converter;

    @BeforeEach
    void setUp() {
        dataAccess = mock(ICurrencyRateConverterDataAccess.class);
        converter = new Converter(dataAccess);

        when(dataAccess.getByCharCode("AUD")).thenReturn(new CurrencyRate("AUD", "Австралийский доллар", 1, BigDecimal.valueOf(53.0061)));
        when(dataAccess.getByCharCode("AZN")).thenReturn(new CurrencyRate("AZN", "Азербайджанский манат", 1, BigDecimal.valueOf(47.8148)));
    }

    @Test
    void getAllCurrenciesWhenEmpty() {
        when(dataAccess.getAll()).thenReturn(List.of());
        ResponseEntity<?> response = converter.getAllCurrencies();
        assertEquals(204, response.getStatusCode().value());
        assertNull(response.getBody());
    }

    @Test
    void getAllCurrenciesWhenFilled() {
        when(dataAccess.getAll()).thenReturn(List.of(
                new CurrencyRate("AUD", "Австралийский доллар", 1, BigDecimal.valueOf(53.0061)),
                new CurrencyRate("AZN", "Азербайджанский манат", 1, BigDecimal.valueOf(47.8148))
        ));

        ResponseEntity<?> response = converter.getAllCurrencies();
        List<CurrencyRateDtoModel> responseBody = (List<CurrencyRateDtoModel>) response.getBody();

        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, responseBody.size());
    }

    @Test
    void convertCurrenciesWhenCurrencyNotFound() {
        when(dataAccess.getByCharCode("QWE")).thenReturn(null);

        ConversionRequestModel request = new ConversionRequestModel("AUD", "QWE", new BigDecimal("10"), null);
        ResponseEntity<?> response = converter.convertCurrencies(request);

        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void convertCurrenciesWhenBothAmountsFilled() {
        ConversionRequestModel request = new ConversionRequestModel("AUD", "AZN", new BigDecimal("10"), new BigDecimal("5"));
        ResponseEntity<?> response = converter.convertCurrencies(request);

        assertEquals(422, response.getStatusCode().value());
    }

    @Test
    void convertCurrenciesWhenFirstAmountNull() {
        ConversionRequestModel request = new ConversionRequestModel("AUD", "AZN", null, new BigDecimal("10"));
        ResponseEntity<?> response = converter.convertCurrencies(request);

        assertEquals(200, response.getStatusCode().value());
        assertInstanceOf(ConversionResponseModel.class, response.getBody());
        ConversionResponseModel responseBody = (ConversionResponseModel) response.getBody();
        assertEquals("AZN", responseBody.fromCurrency());
        assertEquals("AUD", responseBody.toCurrency());
        assertEquals(new BigDecimal("10"), responseBody.fromAmount());
        assertEquals(new BigDecimal("9.02"), responseBody.toAmount());
    }

    @Test
    void convertCurrenciesWhenSecondAmountNull() {
        ConversionRequestModel request = new ConversionRequestModel("AUD", "AZN", new BigDecimal("10"), null);
        ResponseEntity<?> response = converter.convertCurrencies(request);

        assertEquals(200, response.getStatusCode().value());
        assertInstanceOf(ConversionResponseModel.class, response.getBody());
        ConversionResponseModel responseBody = (ConversionResponseModel) response.getBody();
        assertEquals("AUD", responseBody.fromCurrency());
        assertEquals("AZN", responseBody.toCurrency());
        assertEquals(new BigDecimal("10"), responseBody.fromAmount());
        assertEquals(new BigDecimal("11.08"), responseBody.toAmount());
    }

    @Test
    void convertCurrenciesWhenFirstAmountZero() {
        ConversionRequestModel request = new ConversionRequestModel("AUD", "AZN", new BigDecimal("0"), null);
        ResponseEntity<?> response = converter.convertCurrencies(request);

        assertEquals(200, response.getStatusCode().value());
        ConversionResponseModel responseBody = (ConversionResponseModel) response.getBody();
        assertEquals("AUD", responseBody.fromCurrency());
        assertEquals("AZN", responseBody.toCurrency());
        assertEquals(new BigDecimal("0"), responseBody.fromAmount());
        assertEquals(new BigDecimal("0"), responseBody.toAmount());
    }

    @Test
    void convertCurrenciesWhenSecondAmountZero() {
        ConversionRequestModel request = new ConversionRequestModel("AUD", "AZN", null, new BigDecimal("0"));
        ResponseEntity<?> response = converter.convertCurrencies(request);

        assertEquals(200, response.getStatusCode().value());
        ConversionResponseModel responseBody = (ConversionResponseModel) response.getBody();
        assertEquals("AZN", responseBody.fromCurrency());
        assertEquals("AUD", responseBody.toCurrency());
        assertEquals(new BigDecimal("0"), responseBody.fromAmount());
        assertEquals(new BigDecimal("0"), responseBody.toAmount());
    }
}
