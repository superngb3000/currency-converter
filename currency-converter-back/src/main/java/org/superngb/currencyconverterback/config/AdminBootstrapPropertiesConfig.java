package org.superngb.currencyconverterback.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "security.admin")
@Getter
@Setter
public class AdminBootstrapPropertiesConfig {
    private String username;

    private String password;
}
