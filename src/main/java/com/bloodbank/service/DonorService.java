package com.bloodbank.service;

import com.bloodbank.dto.request.DonorRequest;
import com.bloodbank.entity.Donor;
import com.bloodbank.exception.ResourceNotFoundException;
import com.bloodbank.repository.DonorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DonorService {

    private final DonorRepository donorRepository;

    @Transactional
    public Donor create(DonorRequest req) {
        Donor donor = Donor.builder()
                .name(req.getName())
                .phone(req.getPhone())
                .bloodType(req.getBloodType())
                .email(req.getEmail())
                .build();
        return donorRepository.save(donor);
    }

    public Donor getById(Long id) {
        return donorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Donor not found: " + id));
    }

    public Page<Donor> getAll(String search, Pageable pageable) {
        if (search != null && !search.isBlank()) {
            return donorRepository.search(search.trim(), pageable);
        }
        return donorRepository.findAll(pageable);
    }

    @Transactional
    public Donor update(Long id, DonorRequest req) {
        Donor donor = getById(id);
        donor.setName(req.getName());
        donor.setPhone(req.getPhone());
        donor.setBloodType(req.getBloodType());
        donor.setEmail(req.getEmail());
        return donorRepository.save(donor);
    }

    @Transactional
    public void delete(Long id) {
        if (!donorRepository.existsById(id))
            throw new ResourceNotFoundException("Donor not found: " + id);
        donorRepository.deleteById(id);
    }

    public List<Donor> getByBloodType(Donor.BloodType bloodType) {
        return donorRepository.findByBloodType(bloodType);
    }
}
