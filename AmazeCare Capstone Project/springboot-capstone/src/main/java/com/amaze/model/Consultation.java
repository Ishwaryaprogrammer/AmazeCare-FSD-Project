package com.amaze.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Setter
@Getter
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

}
