package ru.cbr.parserservicecurrency.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.cbr.parserservicecurrency.model.CurrencyRate;
import ru.cbr.parserservicecurrency.service.CurrencyRateParser;
import ru.cbr.parserservicecurrency.service.ExchangeRateService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/exchange-rates")
public class ExchangeRateController {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyRateParser.class);
    private final ExchangeRateService exchangeRateService;

    public ExchangeRateController(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @GetMapping
    public List<CurrencyRate> getAllExchangeRates() {
        return exchangeRateService.getAllExchangeRates();
    }

    @GetMapping("/{charCode}")
    public ResponseEntity<CurrencyRate> getExchangeRateByCharCode(@PathVariable String charCode) {
        try {
            CurrencyRate currencyRate = exchangeRateService.getCurrencyRate(charCode);
            if (currencyRate == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(currencyRate);
        } catch (Exception e) {
            // Логируем ошибку
            logger.error("Error while fetching exchange rate for charCode: " + charCode, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
