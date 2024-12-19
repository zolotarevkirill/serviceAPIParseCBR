package ru.cbr.parserservicecurrency.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import ru.cbr.parserservicecurrency.model.CurrencyRate;
import ru.cbr.parserservicecurrency.model.CurrencyRateKey;

import java.util.Optional;

@Repository
public interface CurrencyRateRepository extends CassandraRepository<CurrencyRate, CurrencyRateKey> {}