package com.exchangeRates.validator.annotation;

import com.exchangeRates.validator.CurrenciesValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CurrenciesValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrenciesConstraint {
    String message() default "Choose at least one currency";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}