package com.hiretrack.repository;

import com.hiretrack.model.JobSeeker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeekerRepository extends JpaRepository<JobSeeker,Integer> {

    JobSeeker findByUserUsername(String name);
}
