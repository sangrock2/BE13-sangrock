package org.example.springfirst.ch01.ex_1_1.repository;

import org.example.springfirst.ch01.ex_1_1.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

}
