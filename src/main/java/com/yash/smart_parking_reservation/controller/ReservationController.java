package com.yash.smart_parking_reservation.controller;

import com.yash.smart_parking_reservation.dto.CreateReservationRequest;
import com.yash.smart_parking_reservation.dto.ReservationResponse;
import com.yash.smart_parking_reservation.dto.ReservationSummaryResponse;
import com.yash.smart_parking_reservation.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationSummaryResponse> createReservation(
            @Valid @RequestBody CreateReservationRequest request,
            Authentication authentication) {
        String email = authentication.getName();
        ReservationSummaryResponse response = reservationService.createReservation(request, email);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/my")
    public ResponseEntity<List<ReservationResponse>> getMyReservations(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(reservationService.getMyReservations(email));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponse> getReservationById(
            @PathVariable Long id,
            Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(reservationService.getReservationById(id, email));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelReservation(
            @PathVariable Long id,
            Authentication authentication) {
        String email = authentication.getName();
        reservationService.cancelReservation(id, email);
        return ResponseEntity.noContent().build();
    }
}
