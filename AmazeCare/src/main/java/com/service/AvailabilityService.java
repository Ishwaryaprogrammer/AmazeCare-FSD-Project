package com.service;

import com.enums.Day;
import com.exception.ResourceNotFoundException;
import com.exception.TimeOverlapException;
import com.model.Availability;
import com.util.Slot;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AvailabilityService {
    private final Session session;


    public AvailabilityService(Session session) {
        this.session = session;
    }

    public void addAvailability(Availability availability) throws TimeOverlapException {

        if(availability.getEndTime().isBefore(availability.getStartTime()) || availability.getEndTime().equals(availability.getStartTime())){
            throw new RuntimeException("Invalid timing. Endtime must be after start time");
        }
        List<Availability> availabilities=getAllForMe(availability.getDoctor().getId()).stream().filter(availability2-> availability2.getDay().equals(availability.getDay())).toList();
        for(Availability old:availabilities){
            if(availability.getStartTime().isBefore(old.getEndTime()) &&
                    availability.getEndTime().isAfter(old.getStartTime())){
                throw new TimeOverlapException("Timings of this availability overlaps with others.");

            }
        }
        Transaction tx=session.beginTransaction();
        session.persist(availability); // should not clash
        tx.commit();
    }

    public Availability getById(int id, int docterId) throws ResourceNotFoundException {

        Transaction tx=session.beginTransaction();
        Availability availability =session.find(Availability.class,id);
        tx.commit();
        if(availability==null){

            throw new ResourceNotFoundException("Availability id not found");
        }
        if(availability.getDoctor().getId() != docterId){
            throw new ResourceNotFoundException("It is not in your Appointments list");
        }
        return availability;
    }

    public void updateAvailability(Availability availability) throws TimeOverlapException {
        if(availability.getEndTime().isBefore(availability.getStartTime()) || availability.getEndTime().equals(availability.getStartTime())){
            throw new RuntimeException("Invalid timing. Endtime must be after start time");
        }
        List<Availability> availabilities=getAllForMe(availability.getDoctor().getId()).stream().filter(availability2-> availability2.getDay().equals(availability.getDay())).toList();
        availabilities=availabilities.stream().filter(availability1 -> availability1.getId() != availability.getId()).toList();
        for(Availability old:availabilities){
            if(availability.getStartTime().isBefore(old.getEndTime()) &&
                    availability.getEndTime().isAfter(old.getStartTime())){
                throw new TimeOverlapException("Timings of this availability overlaps with others.");

            }
        }
        Transaction tx=session.beginTransaction();
        session.merge(availability); // should not clash
        tx.commit();
    }

    public void delete(int id2) {

        Transaction tx=session.beginTransaction();
        session.createMutationQuery("delete from Availability where id=:id2").setParameter("id",id2).executeUpdate();
        tx.commit();
    }

    public List<Availability> getAll() {
        Transaction tx=session.beginTransaction();
        List<Availability> availabilities=session.createQuery("from Availability", Availability.class).list();
        tx.commit();
        return availabilities;
    }

    public List<Availability> getAllForMe(int doctorId) {
        Transaction tx=session.beginTransaction();
        List<Availability> availabilities=session.createQuery("from Availability where doctor.id=:doctorId", Availability.class).setParameter("doctorId",doctorId).list();
        tx.commit();
        return availabilities;
    }



    public List<Slot> listAvailableSlots(int docid, Day day, List<LocalTime> startTimingsOfAppointments) {

        Transaction tx=session.beginTransaction();
        List<Availability> availabilities=session.createQuery("from Availability where doctor.id=:docid and day=:day", Availability.class)
                .setParameter("docid",docid)
                .setParameter("day",day).
                list();
        List<Slot> slot=new ArrayList<>();
        for(Availability a:availabilities){
            int duration=a.getDuration();
            LocalTime startTime=a.getStartTime();
            LocalTime endTime=a.getEndTime();

            LocalTime i=startTime;
            while(i.isBefore(endTime.minusMinutes(duration)) || i.equals(endTime.minusMinutes(duration))){

                if(!startTimingsOfAppointments.contains(i)){
                    slot.add(new Slot(i,i.plusMinutes(duration),duration));
                }
                i=i.plusMinutes(duration);
            }

        }


        tx.commit();
        return slot;
    }
}
