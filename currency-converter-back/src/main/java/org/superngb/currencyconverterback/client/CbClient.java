package org.superngb.currencyconverterback.client;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.superngb.currencyconverterback.config.CbPropertiesConfig;
import org.superngb.currencyconverterback.dto.CbResponseModel;

@Component
@RequiredArgsConstructor
public class CbClient {

    private final CbPropertiesConfig cbProperties;

    private final RestTemplate restTemplate;

    public CbResponseModel getCurrencyRates() {
        return restTemplate.getForObject(cbProperties.getUrl(), CbResponseModel.class);
    }
}
