package com.exchangeRates.service.impl;

import com.exchangeRates.entity.Currency;
import com.exchangeRates.service.CurrencyService;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Service
@PropertySource("classpath:currency.properties")
public class CurrencyServiceImpl implements CurrencyService {

    @Value("${currency.accessKey}")
    private  String ACCESS_KEY;

    @Value("${currency.url}")
    private String BASE_URL;

    @Value("${currency.endpoint.live}")
    private String LIVE_ENDPOINT;

    @Value("${currency.endpoint.historical}")
    private String HISTORICAL_ENDPOINT;

    private String date;

    private CloseableHttpClient httpClient;

    public CurrencyServiceImpl() {
        this.httpClient = HttpClients.createDefault();
    }

    private Map<Currency, Double> sendRequestForCurrencies(String url, List<Currency> userCurrencies) {

        HttpGet get = new HttpGet(url);

        Map<Currency, Double> currencies = null;
        try(CloseableHttpResponse response = httpClient.execute(get)) {
            HttpEntity entity = response.getEntity();
            JSONObject exchangeRates = new JSONObject(EntityUtils.toString(entity));

            setDate(exchangeRates.getLong("timestamp"));

            String source = exchangeRates.getString("source");
            JSONObject quotes = exchangeRates.getJSONObject("quotes");

            currencies = new LinkedHashMap<>();
            for (Currency currency : userCurrencies) {
                double k = quotes.getDouble(source + currency.getName());
                currencies.put(currency, k);
            }
        } catch (IOException | JSONException | ParseException e) {
            e.printStackTrace();
        }

        return currencies;
    }

    private void setDate(long timestamp) {
        Date timeStampDate = new Date(timestamp * 1000);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
        this.date = dateFormat.format(timeStampDate);
    }

    @Override
    public String getDate() {
        return date;
    }

    private String currenciesToString(List<Currency> userCurrencies) {
        StringBuilder currenciesString = new StringBuilder();
        for (Currency currency : userCurrencies) {
            currenciesString
                    .append(currency).append(",");
        }
        currenciesString.deleteCharAt(currenciesString.length()-1);
        return currenciesString.toString();
    }

    private String createUrl(List<Currency> userCurrencies) {
        return BASE_URL + LIVE_ENDPOINT + "?access_key="
                + ACCESS_KEY + "&currencies="
                + currenciesToString(userCurrencies);
    }

    private String createUrl(List<Currency> userCurrencies, LocalDate date) {
        return BASE_URL + HISTORICAL_ENDPOINT + "?access_key="
                + ACCESS_KEY + "&date=" + date
                + "&currencies=" + currenciesToString(userCurrencies);
    }


    private Map<Currency, Double> getCurrencies(List<Currency> userCurrencies, String url) {
        Map<Currency, Double> currencies =
                this.sendRequestForCurrencies(url, userCurrencies);

        Map<Currency, Double> userMap = new LinkedHashMap<>();
        Set<Currency> keysSet = currencies.keySet();

        for (Currency currency : userCurrencies) {
            keysSet.stream()
                    .filter(c -> c.equals(currency))
                    .forEach(c -> userMap.put(c, currencies.get(c)));
        }
        return userMap;
    }

    @Override
    public Map<Currency, Double> getCurrencies(List<Currency> userCurrencies) {
        return getCurrencies(userCurrencies, createUrl(userCurrencies));
    }

    @Override
    public Map<Currency, Double> getCurrencies(List<Currency> userCurrencies, LocalDate date) {
        return getCurrencies(userCurrencies, createUrl(userCurrencies, date));
    }

    @PreDestroy
    private void close() {
        try {
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}