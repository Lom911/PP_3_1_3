package ru.kata.spring.boot_security.demo.repository;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.Collection;
import java.util.Set;

@Repository
public interface RoleDao {

    void add(Role role);

    Role findByRoleName(String role);

    Collection<Role> getAllRoles();

    Set<Role> getSetOfRole(String[] roleName);

    Role getById(int id);

}
