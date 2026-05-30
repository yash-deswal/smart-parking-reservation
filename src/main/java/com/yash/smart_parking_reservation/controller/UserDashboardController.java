package com.yash.smart_parking_reservation.controller;

import com.yash.smart_parking_reservation.dto.UserDashboardResponse;
import com.yash.smart_parking_reservation.entity.ReservationStatus;
import com.yash.smart_parking_reservation.entity.User;
import com.yash.smart_parking_reservation.repository.ParkingSlotRepository;
import com.yash.smart_parking_reservation.repository.ReservationRepository;
import com.yash.smart_parking_reservation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/dashboard")
@RequiredArgsConstructor
public class UserDashboardController {

    private final UserRepository userRepository;
    private final ParkingSlotRepository parkingSlotRepository;
    private final ReservationRepository reservationRepository;

    @GetMapping
    public ResponseEntity<UserDashboardResponse> getUserDashboard(Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        long availableSlots = parkingSlotRepository.countByReservedAndActive(false, true);
        long reservedSlots = parkingSlotRepository.countByReserved(true);
        long myActiveReservations = reservationRepository.countByUserAndStatus(user, ReservationStatus.ACTIVE);

        UserDashboardResponse response = UserDashboardResponse.builder()
                .availableSlots(availableSlots)
                .reservedSlots(reservedSlots)
                .myActiveReservations(myActiveReservations)
                .build();

        return ResponseEntity.ok(response);
    }
}
