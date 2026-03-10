package com.bloodbank.dto.request;

import com.bloodbank.entity.Donor;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class BloodDonationRequest {
    @NotNull
    private Long donorId;

    @NotNull
    private Donor.BloodType bloodType;

    @NotNull @Min(1) @Max(10)
    private Integer quantity;

    @NotNull @PastOrPresent
    private LocalDate donationDate;
}
