package org.superngb.currencyconverterback.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "cb.base-currency")
@Getter
@Setter
public class BaseCurrencyConfig {

    private String charCode;

    private String name;
}
