package com.yash.smart_parking_reservation.repository;

import com.yash.smart_parking_reservation.entity.ParkingSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, Long> {
    boolean existsBySlotNumber(String slotNumber);
    long countByActive(boolean active);
    long countByReserved(boolean reserved);
    long countByReservedAndActive(boolean reserved, boolean active);
}
