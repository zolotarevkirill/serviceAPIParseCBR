package ru.cbr.parserservicecurrency.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.cbr.parserservicecurrency.model.CurrencyRate;
import ru.cbr.parserservicecurrency.repository.CurrencyRateRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ExchangeRateService {

    @Autowired
    private CurrencyRateRepository currencyRateRepository;

    public List<CurrencyRate> getAllExchangeRates() {
        return currencyRateRepository.findAll();
    }

    public CurrencyRate getCurrencyRate(String charCode) {
        CurrencyRate latestCurrencyRate = currencyRateRepository.findByKey(charCode);

        if (latestCurrencyRate == null) {
            System.out.println("Currency rate not found for charCode: " + charCode);
        }

        return latestCurrencyRate;
    }
}
