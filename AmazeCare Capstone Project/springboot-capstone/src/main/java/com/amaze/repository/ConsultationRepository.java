package com.amaze.repository;

import com.amaze.enums.Specialty;
import com.amaze.model.Consultation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConsultationRepository extends JpaRepository<Consultation,Integer> {

    Optional<Consultation> findByAppointmentId(int appId);


    Page<Consultation> findAllByAppointmentPatientUserName(String name, Pageable pageable);


    Page<Consultation> findAllByAppointmentPatientUserNameAndAppointmentDoctorSpecialty(String name, Specialty specialty, Pageable pageable);
}
