package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleDao;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleDao roleDao;

    @Autowired
    public AdminController(UserService userService, RoleDao roleDao) {
        this.userService = userService;
        this.roleDao = roleDao;
    }

    @GetMapping
    public String getList(@ModelAttribute User user, Principal principal, Model model) {
        Set<Role> rolesAll = new HashSet<>(roleDao.getAllRoles());
        model.addAttribute("userLogged", userService.findByEmail(principal.getName()));
        model.addAttribute("allRoles", rolesAll);
        model.addAttribute("usersAll", userService.listUsers());
        return "users";
    }

    @GetMapping("/id/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        model.addAttribute("usr", userService.getId(id));
        return "show";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user, Model model) {
        Set<Role> allRoles = new HashSet<>(roleDao.getAllRoles());
        model.addAttribute("allRoles", allRoles);
        return "new";
    }


    @PostMapping
    public String create(@ModelAttribute("user") User user,
                         @RequestParam("rolesChecked") String[] rolesArray) {
        /*Set<Role> roleUserForCreate = Arrays.stream(rolesArray)
                .map(roleDao::findByRoleName).collect(Collectors.toSet());*/
        user.setRoles(roleDao.getSetOfRole(rolesArray));
        userService.add(user);
        return "redirect:/admin";
    }


    @GetMapping("/id/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id) {
        User userUpdate = userService.getId(id);
        model.addAttribute("user", userUpdate);
        model.addAttribute("allRoles", new HashSet<>(roleDao.getAllRoles()));
        model.addAttribute("userRoles", new HashSet<>(userUpdate.getRoles()).stream()
                .map(Role::getRole).collect(Collectors.toSet()));
        return "edit";
    }


    @PatchMapping("/id/{id}")
    public String update(@ModelAttribute("user") User user,
                         @PathVariable("id") long id,
                         @RequestParam("rolesChecked") String[] rolesArray) {
        /*Set<Role> roleUserForCreate = Arrays.stream(rolesArray)
               .map(roleDao::findByRoleName).collect(Collectors.toSet());*/
        user.setRoles(roleDao.getSetOfRole(rolesArray));
        userService.update(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/id/{id}")
    public  String delete(@PathVariable("id") long id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}
