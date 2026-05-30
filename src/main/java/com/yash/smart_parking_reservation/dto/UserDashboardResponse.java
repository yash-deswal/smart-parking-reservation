package com.yash.smart_parking_reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDashboardResponse {
    private long availableSlots;
    private long reservedSlots;
    private long myActiveReservations;
}
