package org.example.springfirst.ch06.ex_6_3.service;

import org.example.springfirst.ch06.ex_6_3.domain.User;

import java.sql.SQLException;

public interface UserService {
    void add(User user) throws SQLException, ClassNotFoundException;
    void upgradeLevels();
}
