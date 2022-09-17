package ru.kata.spring.boot_security.demo.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class RoleDaoImpl implements RoleDao{

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public void add(Role role) {
        em.persist(role);
    }

    @Transactional
    @Override
    public Role findByRoleName(String roleName) {
        TypedQuery<Role> query = em.createQuery("SELECT role FROM Role role WHERE role.role=:r", Role.class);
        query.setParameter("r", roleName);
        Role role = query.getSingleResult();
        return role;
    }

    @Transactional
    @Override
    public Collection<Role> getAllRoles() {
        TypedQuery<Role> query = em.createQuery("SELECT role FROM Role role", Role.class);
        return query.getResultList();
    }

    @Transactional
    @Override
    public Set<Role> getSetOfRole(String[] rolesArray) {
        /*Set<Role> roleSet = new HashSet<>();
        for (String role:rolesArray) {
            roleSet.add(findByRoleName(role));
        }
        return roleSet;*/
        return Arrays.stream(rolesArray).map(this::findByRoleName).collect(Collectors.toSet());
    }

    @Override
    public Role getById(int id) {
        return em.find(Role.class,id);
    }


}
