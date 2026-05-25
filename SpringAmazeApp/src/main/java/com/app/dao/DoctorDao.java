package com.app.dao;

import com.app.model.Doctor;

import java.util.List;

public interface DoctorDao {
    public void insert(Doctor doctor);
    public void deleteById(int id);
    public List<Doctor> getAll();
    public Doctor getById(int id);
    public void update(int id, Doctor doctor);


}
