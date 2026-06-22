package com.amaze.repository;

import com.amaze.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Integer> {

    Optional<Patient> findByUserName(String name);


}
