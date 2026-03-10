package com.bloodbank.controller;

import com.bloodbank.dto.request.BloodDonationRequest;
import com.bloodbank.service.BloodDonationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/donations")
@RequiredArgsConstructor
public class BloodDonationController {

    private final BloodDonationService donationService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(donationService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(donationService.getById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody BloodDonationRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(donationService.create(req));
    }
}
