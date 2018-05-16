package com.exchangeRates.service.impl;

import com.exchangeRates.dao.CurrencyDao;
import com.exchangeRates.dao.RoleDao;
import com.exchangeRates.dao.UserDao;
import com.exchangeRates.entity.Role;
import com.exchangeRates.entity.User;
import com.exchangeRates.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;
    private CurrencyDao currencyDao;
    private RoleDao roleDao;

    @Autowired
    public UserServiceImpl(UserDao userDao, CurrencyDao currencyDao, RoleDao roleDao) {
        this.userDao = userDao;
        this.currencyDao = currencyDao;
        this.roleDao = roleDao;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user;
        try {
            user = userDao.findByName(s);
        }
        catch (EmptyResultDataAccessException e) {
            throw new UsernameNotFoundException("No user with name " + s);
        }
        if (user == null)
            throw new UsernameNotFoundException("No user with name " + s);

        user.setAuthorities(roleDao.findAllByUser(user));
        user.setCurrencies(currencyDao.findAllByUser(user));
        return user;
    }

    @Override
    public User create(User user) {
        if(user == null)
            throw  new IllegalArgumentException("User entity is null");

        user = userDao.create(user);
        roleDao.create(Role.USER, user);
        currencyDao.addCurrencies(user);
        return user;
    }


    @Override
    public boolean delete(User user) {
        if(user == null)
            throw  new IllegalArgumentException("User entity is null");
        return delete(user.getId());
    }

    @Override
    public boolean delete(Long id) {
        if (id <= 0)
            throw new IllegalArgumentException("Id parameter is less or equal 0");

        roleDao.delete(id);
        currencyDao.delete(id);
        return userDao.delete(id);
    }

    @Override
    public User findById(Long id) {
        if (id <= 0)
            throw new IllegalArgumentException("Id parameter is less or equal 0");

        User user = userDao.findById(id);

        user.setAuthorities(roleDao.findAllByUser(user));
        user.setCurrencies(currencyDao.findAllByUser(user));
        return user;
    }

    @Override
    public boolean update(User user) {
        if(user == null)
            throw  new IllegalArgumentException("User entity is null");
        User userInfo = (User) this.loadUserByUsername(user.getUsername());
        List<Role> roles = (List<Role>) userInfo.getAuthorities();
        user.setId(userInfo.getId());
        user.setAuthorities(roles);

        roleDao.update(user);
        currencyDao.update(user);
        return userDao.update(user);
    }

    @Override
    public boolean updatePassword(User user, String newPassword) {
        if (user == null)
            throw new IllegalArgumentException("User entity is null");
        if (newPassword == null)
            throw new IllegalArgumentException("New password string is null");

        user.setPassword(newPassword);
        return userDao.updatePassword(user);
    }

    @Override
    public List<User> findAll() {
        List<User> users = userDao.findAll();
        for (User user : users) {
            user.setAuthorities(roleDao.findAllByUser(user));
            user.setCurrencies(currencyDao.findAllByUser(user));
        }
        return users;
    }
}
