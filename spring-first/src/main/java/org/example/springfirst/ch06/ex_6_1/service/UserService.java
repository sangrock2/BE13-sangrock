package org.example.springfirst.ch06.ex_6_1.service;

import org.example.springfirst.ch06.ex_6_1.domain.User;

import java.sql.SQLException;

// UserService를 인터페이스로 두고, 구현을 둘로 나눈다.
// UserServiceImpl : 순수 비즈니스 로직만
// UserServiceTx : 트랜잭션 경계만 책임지고, 실제 일은 다른 UserService에 위임
// 클라이언트는 인터페이스에만 의존하므로 둘의 분리를 몰라도 된다.

public interface UserService {
    void add(User user) throws SQLException, ClassNotFoundException;
    void upgradeLevels();
}
