package org.example.springfirst.oh01.ex_1_1.domain;

// 사용자 정보를 저장할 User 클래스

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
