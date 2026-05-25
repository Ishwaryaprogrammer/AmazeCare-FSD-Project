package com.service;

import com.model.Consultation;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ConsultationService {
    private final Session session;

    public ConsultationService(Session session) {
        this.session = session;
    }

    public void addConsultation(Consultation consultation) {
        Transaction tx = session.beginTransaction();
        Consultation existing = session.createQuery("from Consultation where appointment.id=:id", Consultation.class)
                .setParameter("id", consultation.getAppointment().getId())
                .uniqueResult();
        if(existing != null){
            tx.commit();
            throw new RuntimeException("Consultation already exists");
        }
        session.persist(consultation);
        tx.commit();

    }
}
