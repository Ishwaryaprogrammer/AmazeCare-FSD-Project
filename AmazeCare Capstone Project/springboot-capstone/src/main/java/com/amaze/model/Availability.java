package com.amaze.model;

import com.amaze.enums.Day;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalTime;

@Entity
@Setter
@Getter
public class Availability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated(EnumType.STRING)
    private Day day;
    @Column(nullable = false)
    private LocalTime startTime;
    @Column(nullable = false)
    private LocalTime endTime;
    @Column(nullable = false)
    private int duration;
    @ManyToOne
    private Doctor doctor;

    @CreationTimestamp
    @Column(updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

}
