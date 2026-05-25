package com.service;


import com.exception.ProfileNotFoundException;
import com.model.Patient;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class PatientService {
    private final Session session;

    public PatientService(Session session) {
        this.session = session;
    }


    public void add(Patient patient) {
        Transaction tx=session.beginTransaction();
        session.persist(patient);
        tx.commit();
    }

    public void update(Patient patient) {
        Transaction tx=session.beginTransaction();
        session.merge(patient);
        tx.commit();
    }

    public Patient getProfile(int id) {
        Transaction tx=session.beginTransaction();
        Patient patient = session.createQuery("from Patient where user.id=:id", Patient.class)
                .setParameter("id",id)
                .uniqueResult();
        tx.commit();
        if(patient==null){
            throw new ProfileNotFoundException("Profile not found");
        }
        return patient;
    }
}
