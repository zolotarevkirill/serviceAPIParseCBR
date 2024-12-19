package ru.cbr.parserservicecurrency.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table("currency_rates")
public class CurrencyRate {

    @PrimaryKey
    private CurrencyRateKey key;

    @Column("num_code")
    private Integer numCode;

    @Column("nominal")
    private Integer nominal;

    @Column("name")
    private String name;

    @Column("value")
    private BigDecimal value;

    @Column("vunit_rate")
    private BigDecimal vunitRate;

    @Column("previous_close")
    private BigDecimal previousClose;

    @Column("close")
    private BigDecimal close;
}

