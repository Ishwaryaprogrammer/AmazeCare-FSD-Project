package com.hiretrack.repository;

import com.hiretrack.model.Employer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployerRepository extends JpaRepository<Employer,Integer> {
    @Query("""
            select e from Employer e where e.user.username=?1
            """)
    Employer findByName(String name);
}
