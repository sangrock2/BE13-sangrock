package org.example.springfirst.ch05.ex_5_1.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.example.springfirst.ch05.ex_5_1.dao.Level;

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

    // 다음 레벨로 올린다"는 것은 사용자 데이터에 대한 처리이므로, 그 책임을 User에 둔다.
    // 다음 레벨이 없으면(GOLD) 잘못된 호출이므로 예외로 분명히 알린다.
    // 이렇게 해두면 UserService는 '언제 올릴지'만 판단하고, '어떻게 올릴지'는 User에 맡길 수 있다.
    public void upgradeLevel() {
        this.level = this.level.nextLevel();;
    }
}

