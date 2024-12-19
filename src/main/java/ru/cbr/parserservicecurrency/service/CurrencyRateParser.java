package ru.cbr.parserservicecurrency.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import ru.cbr.parserservicecurrency.model.CurrencyRate;
import ru.cbr.parserservicecurrency.model.CurrencyRateKey;
import ru.cbr.parserservicecurrency.repository.CurrencyRateRepository;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CurrencyRateParser {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyRateParser.class);
    private final CurrencyRateRepository currencyRateRepository;
    private final String serviceUrl = "http://www.cbr.ru/scripts/XML_daily.asp";

    @Autowired
    public CurrencyRateParser(CurrencyRateRepository currencyRateRepository) {
        this.currencyRateRepository = currencyRateRepository;
    }

    @PostConstruct
    @Scheduled(cron = "0 0 * * * *")
    public void parseXMLForCBR() throws ParserConfigurationException, IOException, SAXException {

        if (!isUrlAccessible(serviceUrl)) {
            logger.error("URL is not accessible: {}", serviceUrl);
            return;
        }

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(serviceUrl);
        NodeList nodeList = document.getElementsByTagName("Valute");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            CurrencyRate currencyRate = new CurrencyRate();
            CurrencyRateKey key = new CurrencyRateKey();
            currencyRate.setKey(key);
            key.setCharCode(element.getElementsByTagName("CharCode").item(0).getTextContent());
            key.setTimestamp(LocalDateTime.now());
            currencyRate.setNumCode(Integer.parseInt(element.getElementsByTagName("NumCode").item(0).getTextContent()));
            currencyRate.setNominal(Integer.parseInt(element.getElementsByTagName("Nominal").item(0).getTextContent()));
            currencyRate.setName(element.getElementsByTagName("Name").item(0).getTextContent());
            currencyRate.setVunitRate(new BigDecimal(element.getElementsByTagName("VunitRate").item(0).getTextContent().replace(",", ".")));
            currencyRate.setValue(new BigDecimal(element.getElementsByTagName("Value").item(0).getTextContent().replace(",", ".")));
            currencyRate.setPreviousClose(new BigDecimal(element.getElementsByTagName("Value").item(0).getTextContent().replace(",", ".")));
            currencyRate.setClose(new BigDecimal(element.getElementsByTagName("Value").item(0).getTextContent().replace(",", ".")));

            // Сохранение в репозиторий
            if (currencyRate.getNumCode() == null || currencyRate.getValue() == null) {
                logger.error("NumCode or Value is NULL: {}", currencyRate);
                continue;
            }

            try {
                logger.info("Saving currency rate: {}", currencyRate);
                currencyRateRepository.save(currencyRate);
                logger.info("Successfully saved currency rate: {}", currencyRate.getName());
            } catch (Exception e) {
                logger.error("Error saving currency rate: {}", currencyRate, e);
            }
        }
    }

    private boolean isUrlAccessible(String url) {
        try {
            URL urlObj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();
            return responseCode == HttpURLConnection.HTTP_OK;
        } catch (IOException e) {
            return false;
        }
    }

}
