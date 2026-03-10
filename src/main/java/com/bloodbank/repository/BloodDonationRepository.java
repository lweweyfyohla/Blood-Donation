package com.bloodbank.repository;

import com.bloodbank.entity.BloodDonation;
import com.bloodbank.entity.Donor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BloodDonationRepository extends JpaRepository<BloodDonation, Long> {

    List<BloodDonation> findByDonorId(Long donorId);

    @Query("SELECT d FROM BloodDonation d JOIN FETCH d.donor ORDER BY d.donationDate DESC, d.id DESC")
    List<BloodDonation> findAllWithDonor();

    /** Total units donated per blood type */
    @Query("SELECT d.bloodType AS bloodType, SUM(d.quantity) AS total " +
           "FROM BloodDonation d GROUP BY d.bloodType")
    List<BloodTypeTotal> sumByBloodType();

    interface BloodTypeTotal {
        Donor.BloodType getBloodType();
        Long getTotal();
    }
}
