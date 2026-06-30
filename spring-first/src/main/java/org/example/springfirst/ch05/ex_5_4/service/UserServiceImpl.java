package org.example.springfirst.ch05.ex_5_4.service;

import org.example.springfirst.ch05.ex_5_4.dao.Level;
import org.example.springfirst.ch05.ex_5_4.dao.UserDAO;
import org.example.springfirst.ch05.ex_5_4.domain.User;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
    public static final int MIN_RECOMMEND_FOR_GOLD = 30;

    private UserDAO userDAO;
    private MailSender mailSender;

    public UserServiceImpl(UserDAO userDAO, MailSender mailSender) {
        this.userDAO = userDAO;
        this.mailSender = mailSender;
    }

    @Override
    public void add(User user) throws SQLException, ClassNotFoundException {
        user.setLevel(Level.BASIC);
        userDAO.add(user);
    }

    @Override
    public void upgradeLevels() {
        try {
            List<User> users = userDAO.getAll();

            for (User user : users) {
                if (canUpgrade(user)) {
                    upgradeLevel(user);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Level Upgrade Error & rollback", e);
        }

    }

    public boolean canUpgrade(User user) {
        Level curLevel = user.getLevel();

        switch (curLevel) {
            case BASIC:
                return user.getLogin() >= MIN_LOGCOUNT_FOR_SILVER;
            case SILVER:
                return user.getRecommend() >= MIN_RECOMMEND_FOR_GOLD;
            case GOLD:
                return false;
            default:
                throw new IllegalStateException("Unexpected value: " + curLevel);
        }
    }

    protected void upgradeLevel(User user) throws SQLException, ClassNotFoundException {
        user.upgradeLevel();
        userDAO.update(user);
        sendUpgradeEmail(user);
    }

    // '메일을 만들어 보낸다'까지만 한다. '어떻게 실제로 보내는가'는 주입된 mailSender에 맡긴다.
    private void sendUpgradeEmail(User user) {
        // User에 email 필드가 없으므로 예시로 id를 주소처럼 사용한다(실무라면 user.getEmail()).
        Mail mail = new Mail(
                user.getId(),
                "[안내] 등급이 업그레이드되었습니다",
                user.getName() + "님의 등급이 " + user.getLevel() + " 로 변경되었습니다."
        );
        mailSender.send(mail);
    }
}
