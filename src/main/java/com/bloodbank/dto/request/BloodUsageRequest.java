package com.bloodbank.dto.request;

import com.bloodbank.entity.Donor;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class BloodUsageRequest {
    @NotNull
    private Donor.BloodType bloodType;

    @NotNull @Min(1) @Max(100)
    private Integer quantity;

    @NotNull @PastOrPresent
    private LocalDate usedDate;

    @Size(max = 500)
    private String note;
}
