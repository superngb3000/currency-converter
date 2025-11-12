package org.superngb.currencyconverterback.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "cb")
@Getter
@Setter
public class CbPropertiesConfig {

    private String url;
}
