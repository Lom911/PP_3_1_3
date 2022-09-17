package ru.kata.spring.boot_security.demo.repository;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository

public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager em;


    @Override
    public void add(User user) {
       em.persist(user);
    }

    @Override
    public User getId(long id) {
        User usr = em.find(User.class, id);
        return usr;
    }

    @Override
    public void update(User updUser) {
        em.merge(updUser);
    }

    @Override
    public void delete(long id) {
       em.remove(em.find(User.class, id));
    }

    @Override
    public User findByEmail(String email) {
        TypedQuery<User> query = em.createQuery("SELECT user from User user where user.email=:mail",User.class);
        query.setParameter("mail", email);
        User usr = query.getSingleResult();
        return usr;
    }

    @Override
    public List<User> listUsers() {
        TypedQuery<User> query = em.createQuery("select user from User user", User.class);
        return query.getResultList();
    }


}
