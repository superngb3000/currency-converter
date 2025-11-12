package org.superngb.currencyconverterback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.superngb.currencyconverterback.entity.CurrencyRate;

@Repository
public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, String> {
}
