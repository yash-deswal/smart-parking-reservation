package com.yash.smart_parking_reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {
    private long totalUsers;
    private long totalSlots;
    private long activeSlots;
    private long disabledSlots;
    private long reservedSlots;
    private long availableSlots;
    private long activeReservations;
    private long completedReservations;
    private long cancelledReservations;
}
