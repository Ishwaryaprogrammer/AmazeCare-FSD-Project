package com.amaze.model;
import com.amaze.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Setter
@Getter
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private LocalDate date;
    @Column(nullable = false)
    private LocalTime startTime;
    @Column(nullable = false)
    private LocalTime endTime;
    @Column(nullable = false)
    private String symptoms;
    @Column(nullable = false)
    private String reason;
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne
    private Patient patient;
    @ManyToOne
    private Doctor doctor;

    @CreationTimestamp
    @Column(updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;


}
