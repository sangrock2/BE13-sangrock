package org.example.springfirst.ch06.ex_6_3.service;

import org.example.springfirst.ch06.ex_6_3.dao.Level;
import org.example.springfirst.ch06.ex_6_3.dao.UserDAO;
import org.example.springfirst.ch06.ex_6_3.domain.User;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
    public static final int MIN_RECOMMEND_FOR_GOLD = 30;

    private UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
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
    }


}
