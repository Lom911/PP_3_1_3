package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

     List<User> listUsers();

     User getId(long id);

     void add(User user);

    void update(User updUser);

    void delete(long id);

     User findByEmail (String email);


}
