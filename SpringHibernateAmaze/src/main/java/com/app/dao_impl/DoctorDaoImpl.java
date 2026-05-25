package com.app.dao_impl;

import com.app.dao.DoctorDao;
import com.app.model.Doctor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

@Component
public class DoctorDaoImpl implements DoctorDao {
    @PersistenceContext
    private EntityManager em;
    @Override
    public Doctor getDoctorByMail(String mail) {
        return (Doctor) em.createQuery("select d from Doctor d where d.user.email=:mail")
                .setParameter("mail",mail).getSingleResult();
    }
}
