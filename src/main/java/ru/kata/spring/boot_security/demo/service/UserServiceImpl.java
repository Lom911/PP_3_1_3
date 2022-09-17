package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserDao;

import java.util.List;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public List<User> listUsers() {
        return userDao.listUsers();
    }

    @Override
    public User getId(long id) {
        return userDao.getId(id);
    }

    @Override
    public void add(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.add(user);
    }

    @Override
    public void update(User updUser) {
        String newPassword = updUser.getPassword();
        String oldPassword = userDao.getId(updUser.getId()).getPassword();

        if (newPassword.equals("")) {
            updUser.setPassword(oldPassword);
        } else {
            updUser.setPassword(passwordEncoder.encode(newPassword));
        }
        userDao.update(updUser);
    }

    @Override
    public void delete(long id) {
        userDao.delete(id);
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

}



