package com.amaze.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String testName;

    @Column(nullable = false)
    private LocalDate date; // the date of test taken

    @Column(nullable = false)
    private String fileName;

    private Boolean isActive=true;

    @ManyToOne
    private Patient patient;

    @CreationTimestamp
    @Column(updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;


}
