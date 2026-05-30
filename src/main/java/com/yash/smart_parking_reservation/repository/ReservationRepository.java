package com.yash.smart_parking_reservation.repository;

import com.yash.smart_parking_reservation.entity.ParkingSlot;
import com.yash.smart_parking_reservation.entity.Reservation;
import com.yash.smart_parking_reservation.entity.ReservationStatus;
import com.yash.smart_parking_reservation.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUser(User user);
    
    boolean existsByParkingSlotAndStatus(ParkingSlot parkingSlot, ReservationStatus status);
    
    Page<Reservation> findByStatus(ReservationStatus status, Pageable pageable);
    
    List<Reservation> findByStatus(ReservationStatus status);
    
    long countByStatus(ReservationStatus status);
}
