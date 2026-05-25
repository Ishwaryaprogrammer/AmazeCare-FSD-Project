package com.app.dao;

import com.app.model.Doctor;

public interface DoctorDao {
    Doctor getDoctorByMail(String mail);
}
