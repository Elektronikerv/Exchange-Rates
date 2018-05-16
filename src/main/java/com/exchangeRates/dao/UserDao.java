package com.exchangeRates.dao;

import com.exchangeRates.entity.Currency;
import com.exchangeRates.entity.Role;
import com.exchangeRates.entity.User;

import java.util.List;

public interface UserDao {

    User create(User user);

    User findById(Long id);

    boolean delete(User user);

    boolean delete(Long id);

    boolean update(User user);

    boolean updatePassword(User user);

    User findByName(String name);

    List<User> findAll();

}
