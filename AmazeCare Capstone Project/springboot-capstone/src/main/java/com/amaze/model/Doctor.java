package com.amaze.model;

import com.amaze.enums.Specialty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Setter
@Getter
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String fullName;

    @Enumerated(EnumType.STRING)
    private Specialty specialty;

    @Column(nullable = false)
    private int experience;

    @Column(nullable = false)
    private String qualification;

    @Column(nullable = false)
    private String designation;

    @OneToOne
    private User user;

    @CreationTimestamp
    @Column(updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

}
