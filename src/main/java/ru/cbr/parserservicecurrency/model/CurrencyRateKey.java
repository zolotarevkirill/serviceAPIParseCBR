package ru.cbr.parserservicecurrency.model;


import lombok.Getter;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


@PrimaryKeyClass
public class CurrencyRateKey implements Serializable {

    @PrimaryKeyColumn(name = "char_code", type = PrimaryKeyType.PARTITIONED)
    private String charCode;

    @PrimaryKeyColumn(name = "timestamp", type = PrimaryKeyType.CLUSTERED)
    private LocalDateTime timestamp; // Измените на LocalDateTime


    public String getCharCode() {
        return charCode;
    }

    public void setCharCode(String charCode) {
        this.charCode = charCode;
    }

    public LocalDateTime getTimestamp() {
        return timestamp; // Возвращаем LocalDateTime
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp; // Устанавливаем LocalDateTime
    }
}
