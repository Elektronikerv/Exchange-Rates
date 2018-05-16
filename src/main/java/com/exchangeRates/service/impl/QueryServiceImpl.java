package com.exchangeRates.service.impl;

import com.exchangeRates.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:queries.properties")
public class QueryServiceImpl implements QueryService {

    @Autowired
    private Environment env;

    @Override
    public String getQuery(String queryName) {
        return env.getProperty(queryName);
    }
}
