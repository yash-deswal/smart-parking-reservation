package com.yash.smart_parking_reservation.controller;

import com.yash.smart_parking_reservation.dto.DashboardResponse;
import com.yash.smart_parking_reservation.dto.StatisticsResponse;
import com.yash.smart_parking_reservation.service.AdminDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminDashboardController {

    private final AdminDashboardService adminDashboardService;

    @GetMapping({"/dashboard", "/dashboard/statistics"})
    public ResponseEntity<DashboardResponse> getDashboard() {
        return ResponseEntity.ok(adminDashboardService.getDashboardMetrics());
    }

    @GetMapping("/statistics")
    public ResponseEntity<StatisticsResponse> getStatistics() {
        return ResponseEntity.ok(adminDashboardService.getReservationStatistics());
    }

    @GetMapping("/statistics/occupancy")
    public ResponseEntity<Map<String, Double>> getOccupancy() {
        return ResponseEntity.ok(adminDashboardService.getOccupancyRate());
    }
}
