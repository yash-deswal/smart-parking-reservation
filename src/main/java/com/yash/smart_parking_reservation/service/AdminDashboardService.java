package com.yash.smart_parking_reservation.service;

import com.yash.smart_parking_reservation.dto.DashboardResponse;
import com.yash.smart_parking_reservation.dto.StatisticsResponse;
import com.yash.smart_parking_reservation.entity.ReservationStatus;
import com.yash.smart_parking_reservation.repository.ParkingSlotRepository;
import com.yash.smart_parking_reservation.repository.ReservationRepository;
import com.yash.smart_parking_reservation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminDashboardService {

    private final UserRepository userRepository;
    private final ParkingSlotRepository parkingSlotRepository;
    private final ReservationRepository reservationRepository;

    @Transactional(readOnly = true)
    public DashboardResponse getDashboardMetrics() {
        long totalUsers = userRepository.count();
        long totalSlots = parkingSlotRepository.count();
        long activeSlots = parkingSlotRepository.countByActive(true);
        long disabledSlots = parkingSlotRepository.countByActive(false);
        long reservedSlots = parkingSlotRepository.countByReserved(true);
        long availableSlots = parkingSlotRepository.countByReservedAndActive(false, true);

        long activeReservations = reservationRepository.countByStatus(ReservationStatus.ACTIVE);
        long completedReservations = reservationRepository.countByStatus(ReservationStatus.COMPLETED);
        long cancelledReservations = reservationRepository.countByStatus(ReservationStatus.CANCELLED);

        return DashboardResponse.builder()
                .totalUsers(totalUsers)
                .totalSlots(totalSlots)
                .activeSlots(activeSlots)
                .disabledSlots(disabledSlots)
                .reservedSlots(reservedSlots)
                .availableSlots(availableSlots)
                .activeReservations(activeReservations)
                .completedReservations(completedReservations)
                .cancelledReservations(cancelledReservations)
                .build();
    }

    @Transactional(readOnly = true)
    public StatisticsResponse getReservationStatistics() {
        Map<String, Long> stats = new HashMap<>();
        for (ReservationStatus status : ReservationStatus.values()) {
            stats.put(status.name(), reservationRepository.countByStatus(status));
        }

        return StatisticsResponse.builder()
                .reservationsByStatus(stats)
                .build();
    }

    @Transactional(readOnly = true)
    public Map<String, Double> getOccupancyRate() {
        long totalActiveSlots = parkingSlotRepository.countByActive(true);
        long reservedSlots = parkingSlotRepository.countByReserved(true);

        double occupancyRate = 0.0;
        if (totalActiveSlots > 0) {
            occupancyRate = ((double) reservedSlots / totalActiveSlots) * 100.0;
        }

        Map<String, Double> response = new HashMap<>();
        response.put("occupancyRate", occupancyRate);
        return response;
    }
}
