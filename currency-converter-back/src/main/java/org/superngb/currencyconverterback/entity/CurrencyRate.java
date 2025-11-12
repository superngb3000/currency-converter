package org.superngb.currencyconverterback.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CurrencyRate {

    @Id
    @Column(length = 5)
    private String charCode;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer nominal;

    @Column(nullable = false)
    private Double value;
}
