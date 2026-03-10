package com.bloodbank.controller;

import com.bloodbank.dto.request.DonorRequest;
import com.bloodbank.entity.Donor;
import com.bloodbank.service.BloodDonationService;
import com.bloodbank.service.DonorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/donors")
@RequiredArgsConstructor
public class DonorController {

    private final DonorService donorService;
    private final BloodDonationService donationService;

    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size) {
        // FIX: default size 100 so small deployments don't need pagination handling
        // Also sort by name for consistent ordering
        return ResponseEntity.ok(
                donorService.getAll(search, PageRequest.of(page, size, Sort.by("name"))));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(donorService.getById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody DonorRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(donorService.create(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody DonorRequest req) {
        return ResponseEntity.ok(donorService.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        donorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/donations")
    public ResponseEntity<?> getDonations(@PathVariable Long id) {
        return ResponseEntity.ok(donationService.getByDonor(id));
    }

    @GetMapping("/blood-type/{bloodType}")
    public ResponseEntity<?> getByBloodType(@PathVariable Donor.BloodType bloodType) {
        return ResponseEntity.ok(donorService.getByBloodType(bloodType));
    }
}
