package org.superngb.currencyconverterback;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.superngb.currencyconverterback.domain.parser.IParser;

@SpringBootApplication
public class CurrencyConverterBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyConverterBackApplication.class, args);
	}

	@Bean
	public ApplicationRunner initCurrencyRates(IParser iParser) {
		return args -> iParser.restoreCurrencyRates();
	}
}
