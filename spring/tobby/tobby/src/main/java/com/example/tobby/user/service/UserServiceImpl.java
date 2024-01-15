package com.example.tobby.user.service;

import com.example.tobby.user.dao.UserDao;
import com.example.tobby.user.domain.Level;
import com.example.tobby.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {

  public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
  public static final int MIN_RECCOMEND_FOR_GOLD = 30;
  UserDao userDao;
  @Autowired
  MailSender mailSender;

  public void setUserDao(UserDao userDao) {
    this.userDao = userDao;
  }


  @Override
  public void upgradeLevels() throws SQLException {
    List<User> users = userDao.getAll();
    for (User user : users) {
      if (canUpgradeLevel(user)) {
        upgradeLevel(user);
      }
    }
  }


  protected void upgradeLevel(User user) {
    user.upgradeLevel();
    userDao.update(user);
    sendUpgradeEmail(user);
  }

  public void setMailSender(MailSender mailSender) {
    this.mailSender = mailSender;
  }

  private void sendUpgradeEmail(User user) {
    SimpleMailMessage mailMessage = new SimpleMailMessage();
    mailMessage.setTo(user.getEmail());
    mailMessage.setFrom("jesustark0235@gmail.com");
    mailMessage.setSubject("Upgrade 안내");
    mailMessage.setText("님의 등급이 " + user.getLevel()
                                        .name() + "로 오름");
    mailSender.send(mailMessage);
  }

  private boolean canUpgradeLevel(User user) {
    Level currentLevel = user.getLevel();
    switch (currentLevel) {
      case BASIC:
        return (user.getLogin() >= MIN_LOGCOUNT_FOR_SILVER);
      case SILVER:
        return (user.getRecommend() >= MIN_RECCOMEND_FOR_GOLD);
      case GOLD:
        return false;
      default:
        throw new IllegalArgumentException("Unknown Level: " +
            currentLevel);
    }
  }

  @Override
  public void add(User user) {
    if (user.getLevel() == null) {
      user.setLevel(Level.BASIC);
    }
    userDao.add(user);
  }
}
