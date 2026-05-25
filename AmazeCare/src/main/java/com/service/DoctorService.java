package com.service;

import com.enums.Specialty;
import com.exception.ResourceNotFoundException;
import com.model.Doctor;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class DoctorService {
    private final Session session;

    public DoctorService(Session session) {
        this.session = session;
    }

    public void addDoctor(Doctor doctor){
        Transaction tx=session.beginTransaction();
        session.persist(doctor);
        tx.commit();
    }

    public void deleteById(int id) {
        Transaction tx=session.beginTransaction();
        session.createMutationQuery("delete from Doctor where id=:id").setParameter("id",id).executeUpdate();
        tx.commit();
    }

    public List<Doctor> getAll() {
        Transaction tx=session.beginTransaction();
        List<Doctor> doctors=session.createQuery("from Doctor", Doctor.class).list();
        tx.commit();
        return doctors;
    }

    public List<Doctor> getBySpecialty(Specialty specialty) throws ResourceNotFoundException {
        Transaction tx=session.beginTransaction();
        List<Doctor> doctors=session.createQuery("from Doctor where specialty=:specialty", Doctor.class).setParameter("specialty",specialty).list();
        tx.commit();
        if(doctors.isEmpty()){
            throw new ResourceNotFoundException("Doctors not found");
        }
        return doctors;
    }

    public Doctor getById(int id) throws ResourceNotFoundException {
        Transaction tx=session.beginTransaction();
        Doctor doctor =session.find(Doctor.class,id);
        tx.commit();
        if(doctor==null){

            throw new ResourceNotFoundException("Doctor not found");
        }
        return doctor;
    }

    public void updateDoctor(Doctor doctor1) {
        Transaction tx=session.beginTransaction();
        session.merge(doctor1);
        tx.commit();
    }

    public Doctor getDoctorByUserId(int id) throws ResourceNotFoundException {
        Transaction tx=session.beginTransaction();
        Doctor doctor=session.createQuery("from Doctor where user.id=:id", Doctor.class).setParameter("id",id).uniqueResult();
        tx.commit();
        if(doctor == null){
            throw new ResourceNotFoundException("Doctor not found");
        }
        return doctor;
    }
}
