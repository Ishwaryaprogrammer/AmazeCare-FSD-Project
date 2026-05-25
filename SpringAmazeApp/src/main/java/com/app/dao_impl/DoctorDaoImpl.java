package com.app.dao_impl;

import com.app.dao.DoctorDao;
import com.app.model.Doctor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class DoctorDaoImpl implements DoctorDao {

    private final JdbcTemplate jdbcTemplate;

    public DoctorDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public RowMapper<Doctor> mapper(){
        return (rs,rows) ->{
            return new Doctor(rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("specialty"),
                    rs.getInt("experience"),
                    rs.getString("qualification"),
                    rs.getString("designation"));
        };

    }

    @Override
    public void insert(Doctor doctor) {

        jdbcTemplate.update("insert into doctor(name,specialty,experience, qualification, designation)" +
                " values(?,?,?,?,?)",doctor.getName(),doctor.getSpeciality(),doctor.getExperience(),doctor.getQualification(), doctor.getDesignation());



    }

    @Override
    public void deleteById(int id) {
        jdbcTemplate.update("delete from doctor where id=?",id);

    }

    @Override
    public List<Doctor> getAll() {

        return jdbcTemplate.query("select * from doctor", mapper());

    }

    @Override
    public Doctor getById(int id) {
        return jdbcTemplate.queryForObject("select * from doctor where id=?",mapper(),id);
    }

    @Override
    public void update(int id, Doctor doctor) {

        jdbcTemplate.update("update doctor set name=?, specialty=?,experience=?, qualification=?, designation=? where id=?", doctor.getName(),doctor.getSpeciality(),doctor.getExperience(), doctor.getQualification(), doctor.getDesignation(),id);

    }
}
