package com.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
@Entity
public class Consultation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 1000, nullable = false)
    private String symptoms;
    @Column(length = 1000, nullable = false)
    private String phyExam;
    @Column(length = 1000)
    private String treatment;
    @Column(length = 1000)
    private String recommended;
    @Column(length = 1000, nullable = false)
    private String prescription;
    @OneToOne
    private Appointment appointment;

    @CreationTimestamp
    @Column(updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    public Consultation() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getPhyExam() {
        return phyExam;
    }

    public void setPhyExam(String phyExam) {
        this.phyExam = phyExam;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getRecommended() {
        return recommended;
    }

    public void setRecommended(String recommended) {
        this.recommended = recommended;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    @Override
    public String toString() {
        return "Consultation{" +
                "id=" + id +
                ", symptoms='" + symptoms + '\'' +
                ", phyExam='" + phyExam + '\'' +
                ", treatment='" + treatment + '\'' +
                ", recommended='" + recommended + '\'' +
                ", prescription='" + prescription + '\'' +
                ", appointment=" + appointment +
                '}';
    }
}
