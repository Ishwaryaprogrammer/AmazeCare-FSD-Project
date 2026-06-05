package com.hiretrack.repository;

import com.hiretrack.model.Application;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application,Integer> {


    @Query("""
            select ap from Application ap
            where ap.jobSeeker.user.username=?1
            """)
    Page<Application> findAllByJobSeekerUserUsername(String name, Pageable pageable);


}
