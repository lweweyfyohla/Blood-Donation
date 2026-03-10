package com.bloodbank.service;

import com.bloodbank.dto.request.BloodDonationRequest;
import com.bloodbank.entity.BloodDonation;
import com.bloodbank.entity.Donor;
import com.bloodbank.exception.ResourceNotFoundException;
import com.bloodbank.repository.BloodDonationRepository;
import com.bloodbank.repository.DonorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BloodDonationService {

    private final BloodDonationRepository donationRepository;
    private final DonorRepository donorRepository;

    @Transactional
    public BloodDonation create(BloodDonationRequest req) {
        Donor donor = donorRepository.findById(req.getDonorId())
                .orElseThrow(() -> new ResourceNotFoundException("Donor not found: " + req.getDonorId()));

        Donor.BloodType bloodType = req.getBloodType() != null
                ? req.getBloodType()
                : donor.getBloodType();

        BloodDonation donation = BloodDonation.builder()
                .donor(donor)
                .bloodType(bloodType)
                .quantity(req.getQuantity())
                .donationDate(req.getDonationDate())
                .build();
        return donationRepository.save(donation);
    }

    public List<BloodDonation> getAll() {
        return donationRepository.findAllWithDonor();
    }

    public BloodDonation getById(Long id) {
        return donationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Donation not found: " + id));
    }

    public List<BloodDonation> getByDonor(Long donorId) {
        return donationRepository.findByDonorId(donorId);
    }
}
