package com.yash.smart_parking_reservation.service;

import com.yash.smart_parking_reservation.dto.AdminReservationResponse;
import com.yash.smart_parking_reservation.entity.Reservation;
import com.yash.smart_parking_reservation.entity.ReservationStatus;
import com.yash.smart_parking_reservation.exception.ReservationNotFoundException;
import com.yash.smart_parking_reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminReservationService {

    private final ReservationRepository reservationRepository;

    @Transactional(readOnly = true)
    public Page<AdminReservationResponse> getAllReservations(Pageable pageable) {
        return reservationRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    @Transactional(readOnly = true)
    public AdminReservationResponse getReservationById(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found with id: " + id));
        return mapToResponse(reservation);
    }

    @Transactional(readOnly = true)
    public List<AdminReservationResponse> getReservationsByStatus(String statusStr) {
        ReservationStatus status;
        try {
            status = ReservationStatus.valueOf(statusStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status: " + statusStr);
        }

        return reservationRepository.findByStatus(status).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private AdminReservationResponse mapToResponse(Reservation reservation) {
        return AdminReservationResponse.builder()
                .id(reservation.getId())
                .userId(reservation.getUser().getId())
                .userName(reservation.getUser().getName())
                .userEmail(reservation.getUser().getEmail())
                .slotId(reservation.getParkingSlot().getId())
                .slotNumber(reservation.getParkingSlot().getSlotNumber())
                .vehicleNumber(reservation.getVehicleNumber())
                .vehicleType(reservation.getVehicleType())
                .startTime(reservation.getStartTime())
                .endTime(reservation.getEndTime())
                .status(reservation.getStatus())
                .createdAt(reservation.getCreatedAt())
                .build();
    }
}
