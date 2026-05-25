package com.app.dao;

import com.app.model.Availability;

import java.util.List;

public interface AvailabilityDao {
    void add(Availability availability, String mail);

    List<Availability> getAllForMe(String mail);

    Availability getById(int id2, String mail);

    void delete(int id2);

    void updateAvailability(Availability availability2,String mail);
}
