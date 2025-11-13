package org.superngb.currencyconverterback.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginResponseModel(
        @Schema(
                example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
                description = "JWT токен, используемый для авторизации запросов"
        )
        String token,

        @Schema(
                example = "Bearer",
                description = "Тип токена"
        )
        String tokenType
) {
}
