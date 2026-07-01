package org.example.springfirst.ch06.ex_6_4.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.example.springfirst.ch06.ex_6_4.dao.Level;

@Entity
@Table(name = "users")
@Getter @Setter
public class User {

    @Id
    private String id;

    private String name;
    private String password;
    private Level level;
    private int login;
    private int recommend;

    public void upgradeLevel() {
        this.level = this.level.nextLevel();;
    }
}

