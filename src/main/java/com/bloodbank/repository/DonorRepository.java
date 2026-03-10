package com.bloodbank.repository;

import com.bloodbank.entity.Donor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DonorRepository extends JpaRepository<Donor, Long> {

    @Query("SELECT d FROM Donor d WHERE " +
           "LOWER(d.name) LIKE LOWER(CONCAT('%',:q,'%')) OR " +
           "d.phone LIKE CONCAT('%',:q,'%') OR " +
           "LOWER(d.email) LIKE LOWER(CONCAT('%',:q,'%'))")
    Page<Donor> search(@Param("q") String query, Pageable pageable);

    List<Donor> findByBloodType(Donor.BloodType bloodType);
}
