package com.amaze.repository;

import com.amaze.enums.Status;
import com.amaze.model.Appointment;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment,Integer> {


    @Query("""
             select ap from Appointment ap where ap.doctor.id=?1 and ap.status!="CANCELLED" and ap.date=?2
            """)
    List<Appointment> getAppointmentsStartTimeOnDate(int docId, LocalDate appointmentDate);


    @Query("""
        select a FROM Appointment a
        where a.patient.user.name = ?1
        and a.date = ?2
        and a.status != 'CANCELLED'
    """)
    List<Appointment> findAppointmentsByPatientAndDate(String patientUsername, LocalDate date);

    @Query("""
    from Appointment a
    where a.patient.user.name = ?1
    and (?2 is null or a.date = ?2)
    and (
        ?3 is null
        or lower(a.doctor.fullName)
        like lower(concat('%', ?3, '%'))
    )
""")
    Page<Appointment> findAllByPatientUserName(
            String name,
            LocalDate date,
            String doctorNameSearch,
            Pageable pageable
    );

    @Query("""
    from Appointment a
    where a.patient.user.name = ?1
    and a.status = ?2
    and (?3 is null or a.date = ?3)
    and (
        ?4 is null
        or lower(a.doctor.fullName)
        like lower(concat('%', ?4, '%'))
    )
""")
    Page<Appointment> findAllByPatientUserNameAndStatus(
            String patientName,
            Status status,
            LocalDate date,
            String doctorNameSearch,
            Pageable pageable
    );




    @Query("""
    from Appointment a
    where a.doctor.user.name = ?1
    and a.status = ?2
    and (?3 is null or a.date = ?3)
    and (
        ?4 is null
        or lower(a.patient.fullName)
        like lower(concat('%', ?4, '%'))
    )
""")
    Page<Appointment> findAllByDoctorUserNameAndStatus(
            String doctorName,
            Status status,
            LocalDate date,
            String patientNameSearch,
            Pageable pageable
    );





    @Query("""
    from Appointment a
    where a.doctor.user.name = ?1
    and a.status not in ?2
    and (?3 is null or a.date = ?3)
    and (
        ?4 is null
        or lower(a.patient.fullName)
        like lower(concat('%', ?4, '%'))
    )
""")
    Page<Appointment> findAllByDoctorUserNameAndStatusNotIn(
            String name,
            List<Status> statuses,
            LocalDate date,
            String patientNameSearch,
            Pageable pageable
    );



    @Modifying
    @Transactional
    @Query("""
               update Appointment ap
               set ap.status = com.amaze.enums.Status.CANCELLED
               where ap.date < CURRENT_DATE
               and ap.status in (
                   com.amaze.enums.Status.PENDING,
                   com.amaze.enums.Status.CONFIRMED)
               and ap.doctor.user.name = ?1
       """)
    void setCancel(String name);

    boolean existsByDoctorIdAndDateAndStartTimeAndStatusNot(int docId, LocalDate date, LocalTime startTime, Status status);




    @Query("""
            select count(ap) from Appointment ap where ap.status=?1 and ap.doctor.user.name=?2
            """)
    long getCountAllAppointmentsByStatusForDoctor(Status status, String name);

    @Query("""
            select count(ap) from Appointment ap where ap.status=?1 and ap.patient.user.name=?2
            """)
    long getCountAllAppointmentsByStatusForPatient(Status status, String name);

    @Query("""
            select count(ap) from Appointment ap where ap.doctor.user.name=?1 and ap.status not in ?2
            """)
    long getCountAllUpcomingAppointmentsForDoctor(String name, List<Status> statuses);
}
