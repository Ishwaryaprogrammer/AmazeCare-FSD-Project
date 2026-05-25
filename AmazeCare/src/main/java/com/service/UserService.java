package com.service;

import com.exception.ResourceNotFoundException;
import com.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserService {
    private final Session session;
    public UserService(Session session) {
        this.session = session;
    }

    public void register(User user) {
        Transaction tx=session.beginTransaction();

        User existinguser=session.createQuery("from User where email=:email",User.class).setParameter("email",user.getEmail()).uniqueResult();
        if(existinguser!=null){
            tx.commit();
            throw new RuntimeException("Email already registered");
        }
        session.persist(user);
        tx.commit();
    }

    public User login(String mail, String pass) throws ResourceNotFoundException {
        Transaction tx=session.beginTransaction();
        List<User> l1=session.createQuery("from User where email=:mail and password=:pass", User.class).setParameter("mail",mail)
                .setParameter("pass",pass).list();
        tx.commit();
        if(l1.isEmpty()){
            throw new ResourceNotFoundException("Invalid Credentials");
        }
        return l1.getFirst();







    }

    public void delete(int id) {
        Transaction tx=session.beginTransaction();
        session.createMutationQuery("delete from User where id=:id").setParameter("id",id).executeUpdate();
        tx.commit();
    }
}
