package com.amaze.repository;

import com.amaze.model.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReportRepository extends JpaRepository<Report,Integer> {

    Page<Report> findAllByPatientUserNameAndIsActive(String name, boolean b, Pageable pageable);

    @Query("""
            select count(r) from Report r where r.patient.user.name=?1 and r.isActive=true
            """)
    long getCountFindAllByPatientUserNameAndIsActive(String name);


}
