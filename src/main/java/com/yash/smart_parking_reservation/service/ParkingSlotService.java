package com.yash.smart_parking_reservation.service;

import com.yash.smart_parking_reservation.entity.ParkingSlot;
import com.yash.smart_parking_reservation.exception.SlotAlreadyAvailableException;
import com.yash.smart_parking_reservation.exception.SlotAlreadyReservedException;
import com.yash.smart_parking_reservation.exception.SlotNotFoundException;
import com.yash.smart_parking_reservation.repository.ParkingSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParkingSlotService {

    private final ParkingSlotRepository parkingSlotRepository;

    public List<ParkingSlot> getAllSlots() {
        return parkingSlotRepository.findAll();
    }

    public List<ParkingSlot> getAvailableSlots() {
        return parkingSlotRepository.findByReservedAndActive(false, true);
    }

    @Transactional
    public ParkingSlot reserveSlot(Long id) {
        ParkingSlot slot = parkingSlotRepository.findById(id)
                .orElseThrow(() -> new SlotNotFoundException("Parking slot not found with id: " + id));

        if (slot.isReserved()) {
            throw new SlotAlreadyReservedException("Parking slot " + slot.getSlotNumber() + " is already reserved.");
        }

        slot.setReserved(true);
        return parkingSlotRepository.save(slot);
    }

    @Transactional
    public ParkingSlot releaseSlot(Long id) {
        ParkingSlot slot = parkingSlotRepository.findById(id)
                .orElseThrow(() -> new SlotNotFoundException("Parking slot not found with id: " + id));

        if (!slot.isReserved()) {
            throw new SlotAlreadyAvailableException("Parking slot " + slot.getSlotNumber() + " is already available.");
        }

        slot.setReserved(false);
        return parkingSlotRepository.save(slot);
    }
}
