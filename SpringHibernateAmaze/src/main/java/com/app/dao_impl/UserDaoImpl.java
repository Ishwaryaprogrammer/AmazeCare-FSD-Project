package com.app.dao_impl;

import com.app.dao.UserDao;
import com.app.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private EntityManager em;
    @Override
    public User login(String mail, String pass){
        return  (User) em.createQuery("select u from User u where u.email=:mail and u.password=:pass")
                .setParameter("mail",mail).setParameter("pass",pass).getSingleResult();

    }
}
