package com.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;


@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private LocalDate dob;
    @Column(nullable = false)
    private String gender;
    @Column(nullable = false)
    private String contact;
    @OneToOne
    private User user;

    @CreationTimestamp
    @Column(updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    public Patient() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", dob=" + dob +
                ", gender='" + gender + '\'' +
                ", Contact='" + contact + '\'' +
                ", user=" + user +
                '}';
    }
    public String printForDoctor() {
        return "Patient{" +
                "id=" + id +
                ", dob=" + dob +
                ", gender='" + gender + '\'' +
                ", Contact='" + contact+
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return Objects.equals(dob, patient.dob) && Objects.equals(gender, patient.gender) && Objects.equals(contact, patient.contact) && Objects.equals(user, patient.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dob, gender, contact, user);
    }
}
