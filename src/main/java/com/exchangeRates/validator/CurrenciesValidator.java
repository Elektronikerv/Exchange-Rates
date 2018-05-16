package com.exchangeRates.validator;


import com.exchangeRates.entity.Currency;
import com.exchangeRates.validator.annotation.CurrenciesConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;

public class CurrenciesValidator implements ConstraintValidator<CurrenciesConstraint, Object>{

    @Override
    public void initialize(CurrenciesConstraint currenciesConstraint) {

    }

    @Override
    public boolean isValid(Object currencies, ConstraintValidatorContext constraintValidatorContext) {
        ArrayList<Currency> list = (ArrayList<Currency>) currencies;
        return list.size() > 0;
    }
}
