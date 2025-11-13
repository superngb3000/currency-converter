package org.superngb.currencyconverterback.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "frontend")
@Getter
@Setter
public class FrontendPropertiesConfig {
    private String url;
}
