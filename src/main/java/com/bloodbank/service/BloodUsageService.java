package com.bloodbank.service;

import com.bloodbank.dto.request.BloodUsageRequest;
import com.bloodbank.dto.response.InventoryResponse;
import com.bloodbank.entity.BloodUsage;
import com.bloodbank.entity.Donor;
import com.bloodbank.exception.InsufficientStockException;
import com.bloodbank.exception.ResourceNotFoundException;
import com.bloodbank.repository.BloodDonationRepository;
import com.bloodbank.repository.BloodUsageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BloodUsageService {

    private final BloodUsageRepository usageRepository;
    private final BloodDonationRepository donationRepository;

    @Transactional
    public BloodUsage create(BloodUsageRequest req) {
        // Validate sufficient stock before saving
        InventoryResponse stock = getInventoryByType(req.getBloodType());
        if (stock.getAvailable() < req.getQuantity()) {
            throw new InsufficientStockException(
                "Insufficient stock for " + req.getBloodType() +
                ". Available: " + stock.getAvailable() + ", Requested: " + req.getQuantity());
        }

        BloodUsage usage = BloodUsage.builder()
                .bloodType(req.getBloodType())
                .quantity(req.getQuantity())
                .usedDate(req.getUsedDate())
                .note(req.getNote())
                .build();
        return usageRepository.save(usage);
    }

    public List<BloodUsage> getAll() {
        return usageRepository.findAllOrderByUsedDateDesc();
    }

    public List<InventoryResponse> getInventory() {
        Map<Donor.BloodType, Long> donated = new EnumMap<>(Donor.BloodType.class);
        Map<Donor.BloodType, Long> used    = new EnumMap<>(Donor.BloodType.class);

        for (Donor.BloodType bt : Donor.BloodType.values()) {
            donated.put(bt, 0L);
            used.put(bt, 0L);
        }

        donationRepository.sumByBloodType().forEach(r -> donated.put(r.getBloodType(), r.getTotal()));
        usageRepository.sumByBloodType().forEach(r -> used.put(r.getBloodType(), r.getTotal()));

        List<InventoryResponse> result = new ArrayList<>();
        for (Donor.BloodType bt : Donor.BloodType.values()) {
            long d = donated.get(bt);
            long u = used.get(bt);
            result.add(new InventoryResponse(bt, d, u, Math.max(0, d - u)));
        }
        return result;
    }

    public InventoryResponse getInventoryByType(Donor.BloodType bloodType) {
        return getInventory().stream()
                .filter(i -> i.getBloodType() == bloodType)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Blood type not found: " + bloodType));
    }
}
