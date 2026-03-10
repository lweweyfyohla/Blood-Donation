package com.bloodbank.dto.request;

import com.bloodbank.entity.Donor;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class DonorRequest {
    @NotBlank @Size(min = 2, max = 150)
    private String name;

    @NotBlank @Pattern(regexp = "^\\+?[0-9]{8,15}$", message = "Invalid phone number")
    private String phone;

    @NotNull
    private Donor.BloodType bloodType;

    @Email
    private String email;
}
