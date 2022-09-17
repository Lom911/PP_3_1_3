package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleDao;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;


@Component
public class InitUsers {

    private final UserService userService;
    private final RoleDao roleDao;


    @Autowired
    public InitUsers(UserService userService, RoleDao roleDao) {
        this.userService = userService;
        this.roleDao = roleDao;
    }

    @Transactional
    @PostConstruct
    public void addInitUsersToDB() {
        Role role_Admin = new Role("ROLE_ADMIN");
        roleDao.add(role_Admin);
        Role role_User = new Role("ROLE_USER");
        roleDao.add(role_User);

        Set<Role> rList1 = new HashSet<>();
        rList1.add(role_Admin);
        Set<Role> rList2 = new HashSet<>();
        rList2.add(role_User);
        Set<Role> rList3 = new HashSet<>();
        rList3.add(role_User);
        Set<Role> rList4 = new HashSet<>();
        rList4.add(role_User);
        Set<Role> rList5 = new HashSet<>();
        rList5.add(role_Admin);
        rList5.add(role_User);


    }
}
