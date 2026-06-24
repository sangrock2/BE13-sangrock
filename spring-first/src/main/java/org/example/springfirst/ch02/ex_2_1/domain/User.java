package org.example.springfirst.ch02.ex_2_1.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter @Setter
public class User {

    @Id
    private String id;

    private String name;
    private String password;

}

