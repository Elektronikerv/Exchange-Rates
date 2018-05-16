package com.exchangeRates.service;


import com.exchangeRates.entity.Currency;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CurrencyService {
    String getDate();

    Map<Currency, Double> getCurrencies(List<Currency> userCurrencies);

    Map<Currency, Double> getCurrencies(List<Currency> userCurrencies, LocalDate date);
}
