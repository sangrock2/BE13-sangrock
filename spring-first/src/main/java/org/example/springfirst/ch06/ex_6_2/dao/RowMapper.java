package org.example.springfirst.ch06.ex_6_2.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

// 조회 결과의 변하는부분을 담는 전략
public interface RowMapper<T> {
    T mapRow(ResultSet rs) throws SQLException;
}
