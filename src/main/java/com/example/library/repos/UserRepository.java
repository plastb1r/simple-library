package com.example.library.repos;

import java.util.List;
import java.util.Optional;

import com.example.library.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

        @Autowired
        private JdbcTemplate jdbcTemplate;

        public int count() {
                return jdbcTemplate.queryForObject("select count(*) from USERS", Integer.class);
        }

        public List<User> findAll() {
                return jdbcTemplate.query("select user_name, user_password FROM USERS",
                                (rs, rowNum) -> new User(rs.getString("user_name"), rs.getString("user_password")));
        }

        public Optional<User> findByName(String name) {
                User user = null;
                try {
                        user = jdbcTemplate.queryForObject(
                                        "select user_name, user_password FROM USERS WHERE user_name = ?",
                                        new Object[] { name }, (rs, rowNum) -> new User(rs.getString("user_name"),
                                                        rs.getString("user_password")));
                } catch (EmptyResultDataAccessException e) {
                        return Optional.ofNullable(user);
                }
                return Optional.ofNullable(user);
        }

        public int save(User user) {
                return jdbcTemplate.update("insert into USERS (user_name, user_password) values (?,?)", user.getName(),
                                user.getPassword());
        }

        public int update(User user) {
                return jdbcTemplate.update("update USERS set user_password = ? where user_name = ?", user.getPassword(),
                                user.getName());
        }

        public int deleteByName(String name) {
                return jdbcTemplate.update("delete USERS where user_name = ?", name);
        }

}
