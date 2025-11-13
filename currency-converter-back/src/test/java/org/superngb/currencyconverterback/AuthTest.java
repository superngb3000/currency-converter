package org.superngb.currencyconverterback;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.superngb.currencyconverterback.config.JwtUtil;
import org.superngb.currencyconverterback.domain.auth.Auth;
import org.superngb.currencyconverterback.domain.auth.IAuth;
import org.superngb.currencyconverterback.domain.auth.IUserAuthDataAccess;
import org.superngb.currencyconverterback.dto.LoginRequestModel;
import org.superngb.currencyconverterback.dto.LoginResponseModel;
import org.superngb.currencyconverterback.dto.RegistrationRequestModel;
import org.superngb.currencyconverterback.entity.User;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class AuthTest {

    private IUserAuthDataAccess userAuthDataAccess;
    private PasswordEncoder encoder;
    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;
    private IAuth auth;


    @BeforeEach
    void setUp() {
        userAuthDataAccess = mock(IUserAuthDataAccess.class);
        encoder = mock(PasswordEncoder.class);
        authenticationManager = mock(AuthenticationManager.class);
        jwtUtil = mock(JwtUtil.class);
        auth = new Auth(userAuthDataAccess, encoder, authenticationManager, jwtUtil);
    }

    @Test
    void loginSuccess() {
        Authentication authResult = mock(Authentication.class);
        when(authResult.getName()).thenReturn("admin");
        when(authenticationManager.authenticate(
                argThat(a -> a instanceof UsernamePasswordAuthenticationToken token
                        && Objects.equals(token.getPrincipal(), "admin")
                        && Objects.equals(token.getCredentials(), "admin")
                ))).thenReturn(authResult);
        when(jwtUtil.generateToken("admin")).thenReturn("jwt-token");

        LoginRequestModel request = new LoginRequestModel("admin", "admin");
        ResponseEntity<?> response = auth.login(request);
        LoginResponseModel body = (LoginResponseModel) response.getBody();

        assertEquals(200, response.getStatusCode().value());
        assertEquals("jwt-token", body.token());
        assertEquals("Bearer", body.tokenType());
    }

    @Test
    void loginBadCredentials() {
        when(authenticationManager.authenticate(any(Authentication.class))).thenThrow(new BadCredentialsException("Bad Credentials"));

        LoginRequestModel request = new LoginRequestModel("admin", "wrong");

        assertThrows(BadCredentialsException.class, () -> auth.login(request));
    }

    @Test
    void registerUserSuccess() {
        when(userAuthDataAccess.findByUsername("new_user")).thenReturn(null);
        when(encoder.encode("password")).thenReturn("hash_password");

        RegistrationRequestModel request = new RegistrationRequestModel("new_user", "password");
        ResponseEntity<?> response = auth.registerUser(request);

        assertEquals(201, response.getStatusCode().value());
        verify(userAuthDataAccess).saveUser(argThat(user ->
                "new_user".equals(user.getUsername()) && "hash_password".equals(user.getPassword())
        ));
    }

    @Test
    void registerUserConflict() {
        RegistrationRequestModel request = new RegistrationRequestModel("admin", "admin");
        when(userAuthDataAccess.findByUsername("admin")).thenReturn(new User());

        ResponseEntity<?> response = auth.registerUser(request);

        assertEquals(409, response.getStatusCode().value());
        verify(userAuthDataAccess, never()).saveUser(any());
    }

}
