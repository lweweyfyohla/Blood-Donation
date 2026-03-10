package com.bloodbank.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "blood_donations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BloodDonation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "donor_id", nullable = false)
    @JsonIgnoreProperties({"donations"})
    private Donor donor;

    @Enumerated(EnumType.STRING)
    @Column(name = "blood_type", nullable = false, length = 10)
    private Donor.BloodType bloodType;

    /** Number of units donated (1 unit ≈ 450 ml) */
    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "donation_date", nullable = false)
    private LocalDate donationDate;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
