package ru.kata.spring.boot_security.demo.repository;


import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;


@Repository
public interface UserDao  {

    List<User> listUsers();

    User getId(long id);

    void add(User user);

    void update(User updUser);

    void delete(long id);

    User findByEmail (String email);
}
