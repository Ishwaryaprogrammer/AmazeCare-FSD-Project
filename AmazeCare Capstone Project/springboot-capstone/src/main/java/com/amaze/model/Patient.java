package com.amaze.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;


@Entity
@Setter
@Getter
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String fullName;
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
