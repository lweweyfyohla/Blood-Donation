package com.bloodbank.dto.response;

import com.bloodbank.entity.Donor;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InventoryResponse {
    private Donor.BloodType bloodType;
    private long donated;
    private long used;
    private long available;
}
