package com.bloodbank.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "blood_usages")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BloodUsage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "blood_type", nullable = false, length = 10)
    private Donor.BloodType bloodType;

    /** Number of units used */
    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "used_date", nullable = false)
    private LocalDate usedDate;

    @Column(length = 500)
    private String note;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
