package com.exchangeRates.dao.impl;

import com.exchangeRates.dao.UserDao;
import com.exchangeRates.entity.Currency;
import com.exchangeRates.entity.Role;
import com.exchangeRates.entity.User;
import com.exchangeRates.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {


    private NamedParameterJdbcTemplate template;
    private SimpleJdbcInsert simpleJdbcInsert;
    private BCryptPasswordEncoder passwordEncoder;
    private QueryService queryService;

    @Autowired
    public UserDaoImpl(DataSource dataSource, QueryService queryService) {
        this.queryService = queryService;
        template = new NamedParameterJdbcTemplate(dataSource);
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public User create(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("username", user.getUsername())
                .addValue("first_name", user.getFirstName())
                .addValue("second_name", user.getSecondName())
                .addValue("password", user.getPassword())
                .addValue("email", user.getEmail());

        Long id = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
        user.setId(id);
        return user;
    }

    @Override
    public User findById(Long id) {
        String findByIdQuery = queryService.getQuery("user.find.byId");
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);
        return template.queryForObject(findByIdQuery, parameters, new UserMapper());
    }

    @Override
    public boolean delete(User user) {
        return delete(user.getId());
    }

    @Override
    public boolean delete(Long id) {
        String deleteQuery = queryService.getQuery("user.delete.byId");
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);
        int deletedRows = template.update(deleteQuery, parameters);
        return deletedRows > 0;
    }

    @Override
    public boolean update(User user) {

        String updateQuery = queryService.getQuery("user.update");

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("username", user.getUsername())
                .addValue("first_name", user.getFirstName())
                .addValue("second_name", user.getSecondName())
                .addValue("email", user.getEmail())
                .addValue("id", user.getId());

        int isUpdated = template.update(updateQuery, parameters);
        return isUpdated > 0;
    }

    @Override
    public boolean updatePassword(User user) {
        String updatePasswordQuery = queryService.getQuery("user.update.password");
        String encodedPassword = passwordEncoder.encode(user.getPassword());

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("password", encodedPassword)
                .addValue("id", user.getId());
        int isUpdated = template.update(updatePasswordQuery, parameters);
        return isUpdated > 0;
    }


    @Override
    public User findByName(String username) {
        String findByIdQuery = queryService.getQuery("user.find.ByName");
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("username", username);
        return template.queryForObject(findByIdQuery, parameters, new UserMapper());
    }

    @Override
    public List<User> findAll() {
        String findByIdQuery = queryService.getQuery("user.findAll");
        return template.query(findByIdQuery, new UserMapper());
    }

    private class UserMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();
            user.setId(resultSet.getLong("id"));
            user.setFirstName(resultSet.getString("first_name"));
            user.setSecondName(resultSet.getString("second_name"));
            user.setEmail(resultSet.getString("email"));
            user.setUsername(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
            user.setAccountNonExpired(true);
            user.setAccountNonLocked(true);
            user.setEnabled(true);
            user.setCredentialsNonExpired(true);
            return user;
        }
    }
}
