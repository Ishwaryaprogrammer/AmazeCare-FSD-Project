package com.app.model;

import org.springframework.stereotype.Component;

@Component
public class Doctor {
    private int id;
    private String name;
    private String speciality;
    private int experience;
    private String qualification;
    private String designation;

    public Doctor() {
    }

    public Doctor(String name, String speciality, int experience, String qualification, String designation) {
        this.name = name;
        this.speciality = speciality;
        this.experience = experience;
        this.qualification = qualification;
        this.designation = designation;
    }

    public Doctor(int id, String name, String speciality, int experience, String qualification, String designation) {
        this.id = id;
        this.name = name;
        this.speciality = speciality;
        this.experience = experience;
        this.qualification = qualification;
        this.designation = designation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", speciality='" + speciality + '\'' +
                ", experience=" + experience +
                ", qualification='" + qualification + '\'' +
                ", designation='" + designation + '\'' +
                '}';
    }
}
