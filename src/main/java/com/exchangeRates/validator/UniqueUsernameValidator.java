package com.exchangeRates.validator;

import com.exchangeRates.entity.User;
import com.exchangeRates.service.UserService;
import com.exchangeRates.validator.annotation.UniqueUsername;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    @Autowired
    private UserService userService;

    @Override
    public void initialize(UniqueUsername uniqueUsername) {}

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        try {
            User user = (User) userService.loadUserByUsername(username);
        }
        catch (UsernameNotFoundException e) {
            return true;
        }
        return false;
    }
}
