package org.superngb.currencyconverterback.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.superngb.currencyconverterback.domain.converter.IConverter;
import org.superngb.currencyconverterback.dto.ConversionRequestModel;
import org.superngb.currencyconverterback.dto.ConversionResponseModel;
import org.superngb.currencyconverterback.dto.CurrencyRateDtoModel;

@Tag(name = "Курсы валют", description = "Работа со списком валют и конвертацией")
@RestController
@RequestMapping("/api/converter")
@RequiredArgsConstructor
public class ConverterController {

    private final IConverter converter;

    @Operation(
            summary = "Список валют",
            description = "Возвращает список доступных валют (код и название)"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список получен",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CurrencyRateDtoModel.class)))),
            @ApiResponse(responseCode = "204", description = "Список пуст"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован"),
    })
    @GetMapping
    public ResponseEntity<?> getAllCurrencies() {
        return converter.getAllCurrencies();
    }

    @Operation(
            summary = "Конвертация валют",
            description = "Конвертирует сумму между двумя валютами. Должно быть заполнено одно из полей суммы"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "ОК",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ConversionResponseModel.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные запроса"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован"),
            @ApiResponse(responseCode = "404", description = "Валюта не найдена"),
            @ApiResponse(responseCode = "422", description = "Нарушено правило: должно быть заполненно только одно поле суммы")
    })
    @PostMapping
    public ResponseEntity<?> convertCurrencies(@Valid @RequestBody ConversionRequestModel conversionRequestModel) {
        return converter.convertCurrencies(conversionRequestModel);
    }
}
