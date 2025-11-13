package org.superngb.currencyconverterback.domain.auth;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.superngb.currencyconverterback.config.JwtUtil;
import org.superngb.currencyconverterback.dto.LoginRequestModel;
import org.superngb.currencyconverterback.dto.LoginResponseModel;
import org.superngb.currencyconverterback.dto.RegistrationRequestModel;
import org.superngb.currencyconverterback.entity.User;

@Service
@RequiredArgsConstructor
public class Auth implements IAuth {

    private final IUserAuthDataAccess userAuthDataAccess;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public ResponseEntity<?> login(LoginRequestModel loginRequestModel) {
        Authentication result = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestModel.username().toLowerCase(), loginRequestModel.password()));
        String token = jwtUtil.generateToken(result.getName());
        return ResponseEntity.ok(new LoginResponseModel(token, "Bearer"));
    }

    @Transactional
    @Override
    public ResponseEntity<?> registerUser(RegistrationRequestModel registrationRequestModel) {
        if (userAuthDataAccess.findByUsername(registrationRequestModel.username().toLowerCase()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Имя пользователя уже занято");
        }

        User user = new User();
        user.setUsername(registrationRequestModel.username().toLowerCase());
        user.setPassword(encoder.encode(registrationRequestModel.password()));
        userAuthDataAccess.saveUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body("Пользователь зарегистрирован");
    }
}
