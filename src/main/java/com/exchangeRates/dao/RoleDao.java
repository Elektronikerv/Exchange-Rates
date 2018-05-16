package com.exchangeRates.dao;

import com.exchangeRates.entity.Role;
import com.exchangeRates.entity.User;

import java.util.List;


public interface RoleDao {

    boolean create(Role role, User user);

    boolean delete(Long userId);

    void update(User user);

    List<Role> findAllByUser(User user);
}
