package com.exchangeRates.dao.impl;

import com.exchangeRates.dao.RoleDao;
import com.exchangeRates.entity.Role;
import com.exchangeRates.entity.User;
import com.exchangeRates.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
public class RoleDaoImpl implements RoleDao {

    private NamedParameterJdbcTemplate template;
    private SimpleJdbcInsert  simpleJdbcInsert;
    private QueryService queryService;

    @Autowired
    public RoleDaoImpl(DataSource dataSource, QueryService queryService) {
        this.queryService = queryService;
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("user_role");
    }

    @Override
    public boolean create(Role role, User user) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("role_id", role.getId())
                .addValue("user_id", user.getId());
        int createdRows = simpleJdbcInsert.execute(parameters);
        return createdRows > 0;
    }

    @Override
    public boolean delete(Long userId) {
        String deleteUserRolesQuery = queryService.getQuery("role.delete.byUserId");
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", userId);
        int deletedRows = template.update(deleteUserRolesQuery, parameters);
        return deletedRows > 0;
    }

    @Override
    public void update(User user) {
        delete(user.getId());
        user.getAuthorities().stream()
                .forEach(r -> create(r, user));
    }

    @Override
    public List<Role> findAllByUser(User user) {
        String findByIdQuery = queryService.getQuery("role.findAll.byUserId");
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", user.getId());
        return template.query(findByIdQuery, parameters, new RoleMapper());
    }

    private class RoleMapper implements RowMapper<Role> {
        @Override
        public Role mapRow(ResultSet resultSet, int i) throws SQLException {
            return Role.findById(resultSet.getLong("role_id"));
        }
    }
}
