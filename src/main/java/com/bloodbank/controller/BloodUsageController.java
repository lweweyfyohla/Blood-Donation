package com.bloodbank.controller;

import com.bloodbank.dto.request.BloodUsageRequest;
import com.bloodbank.entity.Donor;
import com.bloodbank.service.BloodUsageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usages")
@RequiredArgsConstructor
public class BloodUsageController {

    private final BloodUsageService usageService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(usageService.getAll());
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody BloodUsageRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usageService.create(req));
    }

    // ── Inventory ─────────────────────────────────────────────────

    @GetMapping("/inventory")
    public ResponseEntity<?> getInventory() {
        return ResponseEntity.ok(usageService.getInventory());
    }

    @GetMapping("/inventory/{bloodType}")
    public ResponseEntity<?> getInventoryByType(@PathVariable Donor.BloodType bloodType) {
        return ResponseEntity.ok(usageService.getInventoryByType(bloodType));
    }
}
