package org.superngb.currencyconverterback.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestModel(
        @Schema(example = "admin", description = "Логин пользователя")
        @NotBlank
        String username,

        @Schema(example = "admin", description = "Пароль пользователя")
        @NotBlank
        String password
) {
}
