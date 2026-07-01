package org.example.springfirst.ch06.ex_6_4.service;

import org.example.springfirst.ch06.ex_6_4.domain.User;

import java.sql.SQLException;

public interface UserService {
    void add(User user) throws SQLException, ClassNotFoundException;
    void upgradeLevels();
}
