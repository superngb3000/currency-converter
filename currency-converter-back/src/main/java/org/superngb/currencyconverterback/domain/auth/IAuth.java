package org.superngb.currencyconverterback.domain.auth;

import org.springframework.http.ResponseEntity;
import org.superngb.currencyconverterback.dto.LoginRequestModel;
import org.superngb.currencyconverterback.dto.RegistrationRequestModel;

public interface IAuth {
    ResponseEntity<?> login(LoginRequestModel loginRequestModel);

    ResponseEntity<?> registerUser(RegistrationRequestModel registrationRequestModel);
}
