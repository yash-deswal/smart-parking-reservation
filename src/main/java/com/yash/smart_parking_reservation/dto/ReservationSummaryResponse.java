package com.yash.smart_parking_reservation.dto;

import com.yash.smart_parking_reservation.entity.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationSummaryResponse {
    private Long id;
    private ReservationStatus status;
}
