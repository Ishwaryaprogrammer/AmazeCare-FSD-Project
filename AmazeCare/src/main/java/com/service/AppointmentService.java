package com.service;

import com.enums.Status;
import com.exception.ResourceNotFoundException;
import com.model.Appointment;
import com.model.Patient;
import org.hibernate.Session;
import org.hibernate.Transaction;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static com.enums.Status.CANCELLED;
import static com.enums.Status.CONFIRMED;

public class AppointmentService {
    private final Session session;

    public AppointmentService(Session session) {
        this.session = session;
    }


    public void addAppointment(Appointment appointment) {
        Transaction tx=session.beginTransaction();
        session.persist(appointment);
        tx.commit();
    }

    public List<Appointment> getPatientAppointments(Patient patient) {
        Transaction tx=session.beginTransaction();
        List<Appointment> appointments=session.createQuery("from Appointment where patient.id=:id", Appointment.class)
                .setParameter("id",patient.getId()).list();
        tx.commit();
        return appointments;
    }

    public List<Appointment> getPatientConfirmedAppointments(Patient patient) {
        Transaction tx=session.beginTransaction();
        List<Appointment> appointments=session.createQuery("from Appointment where patient.id=:id and status=:status", Appointment.class)
                .setParameter("id",patient.getId()).setParameter("status", CONFIRMED).list();
        tx.commit();
        return appointments;
    }

    public void cancelAppointmentByPatient(int id, int appointmentId) throws ResourceNotFoundException {
        Transaction tx=session.beginTransaction();
        Appointment appointment=  session.createQuery("from Appointment where patient.id=:id and status!=:status and status!=:status2 and id=:aid", Appointment.class)
                        .setParameter("id",id).setParameter("status",Status.CANCELLED).setParameter("status2",Status.COMPLETED)
                        .setParameter("aid",appointmentId).uniqueResult();
        if(appointment==null){
            tx.commit();
            throw new ResourceNotFoundException("Appointment id not found");
        }
        session.createMutationQuery("update Appointment set status=:status where id=:id")
                .setParameter("id",appointmentId).setParameter("status", CANCELLED).executeUpdate();
        tx.commit();
    }

    public void changeAppointmentStatusConfirmed(int docid, int appointmentId) throws ResourceNotFoundException {
        Transaction tx=session.beginTransaction();
        Appointment appointment=  session.createQuery("from Appointment where doctor.id=:id and status=:status and id=:aid", Appointment.class)
                .setParameter("id",docid).setParameter("status",Status.PENDING)
                .setParameter("aid",appointmentId).uniqueResult();
        if(appointment==null){
            tx.commit();
            throw new ResourceNotFoundException("Appointment id not found in pending appointments");
        }
        session.createMutationQuery("update Appointment set status=:status where id=:id")
                .setParameter("id",appointmentId).setParameter("status", CONFIRMED).executeUpdate();
        tx.commit();
    }
    public void changeAppointmentStatusCancelled(int docid, int appointmentId) throws ResourceNotFoundException {
        Transaction tx=session.beginTransaction();
        Appointment appointment=  session.createQuery("from Appointment where doctor.id=:id and status=:status and id=:aid", Appointment.class)
                .setParameter("id",docid).setParameter("status",Status.PENDING)
                .setParameter("aid",appointmentId).uniqueResult();
        if(appointment==null){
            tx.commit();
            throw new ResourceNotFoundException("Appointment id not found in pending appointments");
        }
        session.createMutationQuery("update Appointment set status=:status where id=:id")
                .setParameter("id",appointmentId).setParameter("status", CANCELLED).executeUpdate();
        tx.commit();
    }

    public List<Appointment> getDoctorPendingAppointments(int docid) {
        Transaction tx=session.beginTransaction();
        List<Appointment> appointments=session.createQuery("from Appointment where doctor.id=:id and status=:status", Appointment.class)
                .setParameter("id",docid).setParameter("status", Status.PENDING).list();
        tx.commit();
        return appointments;
    }
    public List<Appointment> getDoctorConfirmedAppointments(int docid) {
        Transaction tx=session.beginTransaction();
        List<Appointment> appointments=session.createQuery("from Appointment where doctor.id=:id and status=:status", Appointment.class)
                .setParameter("id",docid).setParameter("status", CONFIRMED).list();
        tx.commit();
        return appointments;
    }
    public List<Appointment> getDoctorCompletedAppointments(int docid) {
        Transaction tx=session.beginTransaction();
        List<Appointment> appointments=session.createQuery("from Appointment where doctor.id=:id and status=:status", Appointment.class)
                .setParameter("id",docid).setParameter("status", Status.COMPLETED).list();
        tx.commit();
        return appointments;
    }

    public void changeAppointmentStatusCompleted(int appointmentId) {
        Transaction tx=session.beginTransaction();
        session.createMutationQuery("update Appointment set status=:status where id=:id")
                .setParameter("id",appointmentId).setParameter("status", Status.COMPLETED).executeUpdate();
        tx.commit();

    }

    public Appointment checkAppointmentOwnership(int docid, int appId) throws ResourceNotFoundException {
        Transaction tx=session.beginTransaction();
        Appointment appointment=  session.createQuery("from Appointment where doctor.id=:id and status=:status and id=:aid", Appointment.class)
                .setParameter("id",docid).setParameter("status", CONFIRMED)
                .setParameter("aid",appId).uniqueResult();
        if(appointment==null){
            tx.commit();
            throw new ResourceNotFoundException("Appointment id not found in Confirmed Appointments");
        }
        tx.commit();
        return appointment;
    }


    public List<LocalTime> getAppointmentsStartTimeOnDate(LocalDate appointmentDate, int docid) {
        Transaction tx=session.beginTransaction();
        List<Appointment> appointments= session.createQuery("from Appointment where doctor.id=:id and date=:appointmentDate and status!=:status", Appointment.class)
                .setParameter("id",docid)
                .setParameter("status", CANCELLED)
                .setParameter("appointmentDate", appointmentDate).list();
        List<LocalTime> startTimings=new ArrayList<>();
        for(Appointment a:appointments){
            startTimings.add(a.getStartTime());
        }
        tx.commit();
        return startTimings;

    }
}
