package com.app.dao_impl;

import com.app.dao.AvailabilityDao;
import com.app.dao.DoctorDao;
import com.app.exception.TimeOverlapException;
import com.app.model.Availability;
import com.app.model.Doctor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Transactional
public class AvalabilityDaoImpl implements AvailabilityDao {
    @PersistenceContext
    private EntityManager em;
    private DoctorDaoImpl doctorDao;
    @Autowired
    public void setDoctorDaoImpl(DoctorDaoImpl doctorDao) {
        this.doctorDao = doctorDao;
    }


    @Override
    public void add(Availability availability, String mail) {
        Doctor doctor= doctorDao.getDoctorByMail(mail);
        List<Availability> availabilities=getAllForMe(mail).stream().filter(availability2-> availability2.getDay().equals(availability.getDay())).toList();
        for(Availability oldAvailability:availabilities){
            if(!(  ( availability.getStartTime().isAfter(oldAvailability.getStartTime())  && (availability.getStartTime().isAfter(oldAvailability.getEndTime())  || availability.getStartTime().equals(oldAvailability.getEndTime())) )
                    || ( availability.getStartTime().isBefore(oldAvailability.getStartTime())  && (availability.getEndTime().isBefore(oldAvailability.getStartTime())  || availability.getEndTime().equals(oldAvailability.getStartTime())) ))){
                throw new TimeOverlapException("Timings of this availability overlaps with others.");

            }
        }
        availability.setDoctor(doctor);
        em.persist(availability);
    }

    @Override
    public List<Availability> getAllForMe(String mail) {
        return em.createQuery("select a from Availability a where a.doctor.user.email=:mail",Availability.class)
                .setParameter("mail",mail).getResultList();

    }

    @Override
    public Availability getById(int id2, String mail) {
        return (Availability) em.createQuery("select a from Availability a where a.doctor.user.email=:mail and a.id=:id2")
                .setParameter("mail",mail)
                .setParameter("id2",id2)
                .getSingleResult();

    }

    @Override
    public void delete(int id2) {
        em.createQuery("delete from Availability a where a.id=:id2")
                .setParameter("id2",id2).executeUpdate();
    }

    @Override
    public void updateAvailability(Availability availability,String mail) {

        List<Availability> availabilities=getAllForMe(mail).stream().filter(availability2-> availability2.getDay().equals(availability.getDay())).toList();
        availabilities=availabilities.stream().filter(availability1 -> availability1.getId() != availability.getId()).toList();
        for(Availability oldAvailability:availabilities){
            if(!(  ( availability.getStartTime().isAfter(oldAvailability.getStartTime())  && (availability.getStartTime().isAfter(oldAvailability.getEndTime())  || availability.getStartTime().equals(oldAvailability.getEndTime())) )
                    || ( availability.getStartTime().isBefore(oldAvailability.getStartTime())  && (availability.getEndTime().isBefore(oldAvailability.getStartTime())  || availability.getEndTime().equals(oldAvailability.getStartTime())) ))){
                throw new TimeOverlapException("Timings of this availability overlaps with others.");

            }
        }

        em.merge(availability);
    }
}
