package com.exchangeRates.service;

import com.exchangeRates.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


public interface UserService extends UserDetailsService {

    User create(User user);

    boolean delete(User user);

    boolean delete(Long id);

    User findById(Long id);

    boolean update(User user);

    boolean updatePassword(User user, String newPassword);

    List<User> findAll();

}
