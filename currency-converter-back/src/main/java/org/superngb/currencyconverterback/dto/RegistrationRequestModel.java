package org.superngb.currencyconverterback.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record RegistrationRequestModel(
        @Schema(example = "new_login", description = "Логин нового пользователя")
        @NotBlank
        String username,

        @Schema(example = "password", description = "Пароль пользователя")
        @NotBlank
        String password
) {
}
