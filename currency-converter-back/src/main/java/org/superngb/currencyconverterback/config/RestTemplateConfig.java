package org.superngb.currencyconverterback.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder, ObjectMapper objectMapper) {
        MappingJackson2HttpMessageConverter jackson = new MappingJackson2HttpMessageConverter(objectMapper);
        List<MediaType> types = new ArrayList<>(jackson.getSupportedMediaTypes());
        types.add(MediaType.valueOf("application/javascript"));
        types.add(MediaType.TEXT_PLAIN);
        types.add(MediaType.valueOf("application/json;charset=UTF-8"));
        jackson.setSupportedMediaTypes(types);

        return builder
                .additionalMessageConverters(jackson)
                .build();
    }
}
