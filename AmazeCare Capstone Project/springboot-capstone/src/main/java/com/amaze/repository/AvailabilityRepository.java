package com.amaze.repository;

import com.amaze.enums.Day;
import com.amaze.model.Availability;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AvailabilityRepository extends JpaRepository<Availability,Integer> {
    List<Availability> findAllByDoctorId(int docId);

    Page<Availability> findAllByDoctorUserName(String name, Pageable pageable);



    @Query("""
             select av from Availability av where av.doctor.id=?1 and av.day=?2
            """)
    List<Availability> getAvailabilitiesByDayAndDoctorId(int docId, Day day);


    Page<Availability> findAllByDoctorUserNameAndDay(String name, Day day, Pageable pageable);

    boolean existsByIdAndDoctorUserName(int id, String name);
}
