package by.sunshine.service;

import jakarta.annotation.PostConstruct;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class CurrencyService {

    private static final String NationalBankWebsite = "https://www.nbrb.by/statistics/rates/ratesdaily.asp?ysclid=lewxnjhw10671551223";
    private static final String DOLLAR = "//*[@id=\"ratesData\"]/table/tbody/tr[8]";
    private static final String EURO = "//*[@id=\"ratesData\"]/table/tbody/tr[10]/td[3]/div";
    private static final String RUPIAH = "//*[@id=\"ratesData\"]/table/tbody/tr[13]/td[3]/div";
    private static final String RUBLE = "//*[@id=\"ratesData\"]/table/tbody/tr[22]/td[3]/div";
    private static final String POUND = "//*[@id=\"ratesData\"]/table/tbody/tr[28]/td[3]/div";
    private static final String YUAN = "//*[@id=\"ratesData\"]/table/tbody/tr[17]/td[3]/div";

    private final Map<String, Double> currencies = new HashMap<>();


    @PostConstruct
    private void findCurrencies() {
        try {
            Document document = Jsoup.connect(NationalBankWebsite).get();
            Elements dollar = document.selectXpath(DOLLAR);
            Elements euro = document.selectXpath(EURO);
            Elements india = document.selectXpath(RUPIAH);
            Elements rub = document.selectXpath(RUBLE);
            Elements english = document.selectXpath(POUND);
            Elements chine = document.selectXpath(YUAN);
            String infoDollar = dollar.get(0).text().split(" ")[4].replace(",", ".");
            currencies.put("by", 1D);
            currencies.put("us", Double.valueOf(infoDollar));
            currencies.put("eu", Double.valueOf(euro.get(0).text().replace(",", ".")));
            currencies.put("ru", Double.parseDouble(rub.get(0).text().replace(",", ".")) / 100.0);
            currencies.put("in", Double.parseDouble(india.get(0).text().replace(",", ".")) / 100.0);
            currencies.put("gb", Double.valueOf(english.get(0).text().replace(",", ".")));
            currencies.put("cn", Double.parseDouble(chine.get(0).text().replace(",", ".")) / 10.0);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public Optional<Double> getValueOfCurrency(String currency) {
        return Optional.ofNullable(currencies.get(currency));
    }
}
