package com.bloodbank.repository;

import com.bloodbank.entity.BloodUsage;
import com.bloodbank.entity.Donor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BloodUsageRepository extends JpaRepository<BloodUsage, Long> {

    /** All usage records sorted newest first */
    @Query("SELECT u FROM BloodUsage u ORDER BY u.usedDate DESC, u.id DESC")
    List<BloodUsage> findAllOrderByUsedDateDesc();

    /** Total units used per blood type — used for inventory calculation */
    @Query("SELECT u.bloodType AS bloodType, SUM(u.quantity) AS total " +
           "FROM BloodUsage u GROUP BY u.bloodType")
    List<BloodTypeTotal> sumByBloodType();

    /** Total units used across ALL blood types — used by dashboard */
    @Query("SELECT COALESCE(SUM(u.quantity), 0) FROM BloodUsage u")
    long sumTotalQuantity();

    interface BloodTypeTotal {
        Donor.BloodType getBloodType();
        Long getTotal();
    }
}
