package com.yash.smart_parking_reservation.controller;

import com.yash.smart_parking_reservation.dto.AdminReservationResponse;
import com.yash.smart_parking_reservation.service.AdminReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/reservations")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminReservationController {

    private final AdminReservationService adminReservationService;

    @GetMapping
    public ResponseEntity<Page<AdminReservationResponse>> getAllReservations(Pageable pageable) {
        return ResponseEntity.ok(adminReservationService.getAllReservations(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminReservationResponse> getReservationById(@PathVariable Long id) {
        return ResponseEntity.ok(adminReservationService.getReservationById(id));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<AdminReservationResponse>> getReservationsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(adminReservationService.getReservationsByStatus(status));
    }
}
