package com.yash.smart_parking_reservation.dto;

import com.yash.smart_parking_reservation.entity.ReservationStatus;
import com.yash.smart_parking_reservation.entity.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationResponse {
    private Long id;
    private Long userId;
    private String userName;
    private Long slotId;
    private String slotNumber;
    private String vehicleNumber;
    private VehicleType vehicleType;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private ReservationStatus status;
    private LocalDateTime createdAt;
}
