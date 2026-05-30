package com.yash.smart_parking_reservation.service;

import com.yash.smart_parking_reservation.dto.CreateReservationRequest;
import com.yash.smart_parking_reservation.dto.ReservationResponse;
import com.yash.smart_parking_reservation.dto.ReservationSummaryResponse;
import com.yash.smart_parking_reservation.entity.*;
import com.yash.smart_parking_reservation.exception.InvalidReservationException;
import com.yash.smart_parking_reservation.exception.ReservationNotFoundException;
import com.yash.smart_parking_reservation.exception.SlotAlreadyReservedException;
import com.yash.smart_parking_reservation.exception.SlotNotFoundException;
import com.yash.smart_parking_reservation.repository.ParkingSlotRepository;
import com.yash.smart_parking_reservation.repository.ReservationRepository;
import com.yash.smart_parking_reservation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ParkingSlotRepository parkingSlotRepository;
    private final UserRepository userRepository;

    @Transactional
    public ReservationSummaryResponse createReservation(CreateReservationRequest request, String email) {
        if (!request.getEndTime().isAfter(request.getStartTime())) {
            throw new InvalidReservationException("End time must be after start time");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AccessDeniedException("User not found"));

        ParkingSlot slot = parkingSlotRepository.findById(request.getSlotId())
                .orElseThrow(() -> new SlotNotFoundException("Parking slot not found with id: " + request.getSlotId()));

        if (slot.isReserved()) {
            throw new SlotAlreadyReservedException("Parking slot is already reserved");
        }
        
        if (reservationRepository.existsByParkingSlotAndStatus(slot, ReservationStatus.ACTIVE)) {
            throw new SlotAlreadyReservedException("Parking slot has an active reservation");
        }

        slot.setReserved(true);
        parkingSlotRepository.save(slot);

        Reservation reservation = Reservation.builder()
                .user(user)
                .parkingSlot(slot)
                .vehicleNumber(request.getVehicleNumber())
                .vehicleType(request.getVehicleType())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .status(ReservationStatus.ACTIVE)
                .build();

        Reservation savedReservation = reservationRepository.save(reservation);

        return ReservationSummaryResponse.builder()
                .id(savedReservation.getId())
                .status(savedReservation.getStatus())
                .build();
    }

    @Transactional(readOnly = true)
    public List<ReservationResponse> getMyReservations(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AccessDeniedException("User not found"));

        return reservationRepository.findByUser(user).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ReservationResponse getReservationById(Long id, String email) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found with id: " + id));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AccessDeniedException("User not found"));

        if (!reservation.getUser().getId().equals(user.getId()) && user.getRole() != Role.ADMIN) {
            throw new AccessDeniedException("You do not have permission to access this reservation");
        }

        return mapToResponse(reservation);
    }

    @Transactional
    public void cancelReservation(Long id, String email) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found with id: " + id));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AccessDeniedException("User not found"));

        if (!reservation.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You can only cancel your own reservations");
        }

        if (reservation.getStatus() == ReservationStatus.CANCELLED) {
            throw new InvalidReservationException("Reservation is already cancelled");
        }

        reservation.setStatus(ReservationStatus.CANCELLED);
        reservationRepository.save(reservation);

        ParkingSlot slot = reservation.getParkingSlot();
        slot.setReserved(false);
        parkingSlotRepository.save(slot);
    }

    @Transactional(readOnly = true)
    public List<ReservationResponse> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private ReservationResponse mapToResponse(Reservation reservation) {
        return ReservationResponse.builder()
                .id(reservation.getId())
                .userId(reservation.getUser().getId())
                .userName(reservation.getUser().getName())
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
