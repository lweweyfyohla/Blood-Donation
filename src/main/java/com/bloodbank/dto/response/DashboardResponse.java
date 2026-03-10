package com.bloodbank.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DashboardResponse {
    private long totalDonors;
    private long totalDonations;
    private long totalBloodUsed;
    private List<InventoryResponse> inventory;
}
