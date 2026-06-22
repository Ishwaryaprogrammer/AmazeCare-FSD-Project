package com.amaze.repository;

import com.amaze.enums.Specialty;
import com.amaze.model.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DoctorRepository extends JpaRepository<Doctor,Integer> {

    @Query("""
            from Doctor d where
            specialty=?1 and (
            lower(d.fullName) LIKE lower(concat('%', ?2, '%'))
            or lower(d.designation) LIKE lower(concat('%', ?2, '%'))
            or lower(d.qualification) LIKE lower(concat('%', ?2, '%')))
            """)
    Page<Doctor> findAllBySpecialtyWithWord(Specialty specialty, String word, Pageable pageable);


    Doctor findByUserName(String name);

    @Query("""
            from Doctor d where
            lower(d.fullName) LIKE lower(concat('%', ?1, '%'))
            or lower(d.designation) LIKE lower(concat('%', ?1, '%'))
            or lower(d.qualification) LIKE lower(concat('%', ?1, '%'))
            """)
    Page<Doctor> findAllWithWord(String word,Pageable pageable);
}
