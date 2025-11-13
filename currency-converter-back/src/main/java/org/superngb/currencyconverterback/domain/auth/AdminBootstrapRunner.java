package org.superngb.currencyconverterback.domain.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.superngb.currencyconverterback.config.AdminBootstrapPropertiesConfig;
import org.superngb.currencyconverterback.entity.User;

@Component
@RequiredArgsConstructor
public class AdminBootstrapRunner implements ApplicationRunner {

    private final IUserAuthDataAccess userAuthDataAccess;
    private final PasswordEncoder passwordEncoder;
    private final AdminBootstrapPropertiesConfig adminBootstrapPropertiesConfig;

    @Override
    public void run(ApplicationArguments args) {
        String username = adminBootstrapPropertiesConfig.getUsername();
        if (username == null || adminBootstrapPropertiesConfig.getPassword() == null || adminBootstrapPropertiesConfig.getPassword().isBlank()) {
            return;
        }

        if (userAuthDataAccess.findByUsername(username) != null) {
            return;
        }

        User admin = new User();
        admin.setUsername(username);
        admin.setPassword(passwordEncoder.encode(adminBootstrapPropertiesConfig.getPassword()));
        userAuthDataAccess.saveUser(admin);
    }
}
