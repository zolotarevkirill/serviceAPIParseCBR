package ru.cbr.parserservicecurrency.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.cbr.parserservicecurrency.model.CurrencyRate;
import ru.cbr.parserservicecurrency.model.CurrencyRateKey;

@Repository
public interface CurrencyRateRepository extends CassandraRepository<CurrencyRate, CurrencyRateKey> {

    @Query("SELECT * FROM currency_rates WHERE char_code = :charCode ORDER BY timestamp DESC LIMIT 1")
    CurrencyRate findByKey(@Param("charCode") String charCode);
}