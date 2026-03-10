package com.bloodbank.service;

import com.bloodbank.dto.response.DashboardResponse;
import com.bloodbank.repository.BloodDonationRepository;
import com.bloodbank.repository.BloodUsageRepository;
import com.bloodbank.repository.DonorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DashboardService {

    private final DonorRepository donorRepository;
    private final BloodDonationRepository donationRepository;
    private final BloodUsageRepository usageRepository;
    private final BloodUsageService bloodUsageService;

    public DashboardResponse getStats() {
        long totalDonors    = donorRepository.count();
        long totalDonations = donationRepository.count();
        // FIX: Use a single aggregate query instead of findAll().stream().mapToLong()
        long totalUsed      = usageRepository.sumTotalQuantity();

        return new DashboardResponse(
                totalDonors,
                totalDonations,
                totalUsed,
                bloodUsageService.getInventory()
        );
    }
}
