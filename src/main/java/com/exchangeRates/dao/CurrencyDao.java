package com.exchangeRates.dao;


import com.exchangeRates.entity.Currency;
import com.exchangeRates.entity.Role;
import com.exchangeRates.entity.User;

import java.util.List;

public interface CurrencyDao  {

    Currency create(Currency currency, User user);

    boolean delete(Long userId);

    void update(User user);

    List<Currency> findAllByUser(User user);

    void addCurrencies(User user);
}
