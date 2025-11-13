package org.superngb.currencyconverterback.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegistrationRequestModel(
        @Schema(example = "new_login", description = "Логин нового пользователя")
        @NotBlank
        @Size(min = 3)
        String username,

        @Schema(example = "password", description = "Пароль пользователя")
        @NotBlank
        @Size(min = 6)
        String password
) {
}
