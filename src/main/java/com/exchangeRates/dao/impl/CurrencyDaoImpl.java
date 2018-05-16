package com.exchangeRates.dao.impl;


import com.exchangeRates.dao.CurrencyDao;
import com.exchangeRates.entity.Currency;
import com.exchangeRates.entity.User;
import com.exchangeRates.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CurrencyDaoImpl implements CurrencyDao {

    private NamedParameterJdbcTemplate template;
    private SimpleJdbcInsert simpleJdbcInsert;
    private QueryService queryService;

    @Autowired
    public CurrencyDaoImpl(DataSource dataSource, QueryService queryService) {
        this.queryService = queryService;
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("user_currency");

    }

    @Override
    public Currency create(Currency currency, User user) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("user_id", user.getId())
                .addValue("currency_id", currency.getId());
        simpleJdbcInsert.execute(parameters);
        return currency;
    }

    @Override
    public boolean delete(Long userId) {
        String deleteUserCurrenciesQuery = queryService.getQuery("currency.delete.byUserId");
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", userId);
        int deletedRows = template.update(deleteUserCurrenciesQuery, parameters);
        return deletedRows > 0;
    }

    @Override
    public void update(User user) {
        delete(user.getId());
        user.getCurrencies().stream()
                .forEach(c -> create(c, user));
    }

    @Override
    public List<Currency> findAllByUser(User user) {
        String findAllQuery = queryService.getQuery("currency.findAll.byUserId");
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", user.getId());
        return template.query(findAllQuery, parameters, new CurrencyMapper());
    }

    @Override
    public void addCurrencies(User user) {
        user.getCurrencies().stream()
                .forEach(c -> create(c, user));
    }

    private class CurrencyMapper implements RowMapper<Currency> {
        @Override
        public Currency mapRow(ResultSet resultSet, int i) throws SQLException {
            return Currency.findById(resultSet.getLong("currency_id"));
        }
    }
}
