package org.superngb.currencyconverterback.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.superngb.currencyconverterback.domain.auth.IAuth;
import org.superngb.currencyconverterback.dto.LoginRequestModel;
import org.superngb.currencyconverterback.dto.RegistrationRequestModel;

@Tag(name = "Авторизация", description = "Логин и регистрация пользователей")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IAuth iAuth;

    @Operation(summary = "Логин")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешная авторизация"),
            @ApiResponse(responseCode = "401", description = "Пользователь не найден")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestModel loginRequestModel) {
        return iAuth.login(loginRequestModel);
    }

    @Operation(summary = "Регистрация")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Пользователь зарегистрирован"),
            @ApiResponse(responseCode = "409", description = "Логин уже занят")
    })
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegistrationRequestModel registrationRequestModel) {
        return iAuth.registerUser(registrationRequestModel);
    }
}
