package com.yash.smart_parking_reservation.service;

import com.yash.smart_parking_reservation.dto.SlotRequest;
import com.yash.smart_parking_reservation.dto.SlotResponse;
import com.yash.smart_parking_reservation.entity.ParkingSlot;
import com.yash.smart_parking_reservation.exception.DuplicateSlotException;
import com.yash.smart_parking_reservation.exception.SlotNotFoundException;
import com.yash.smart_parking_reservation.repository.ParkingSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminSlotService {

    private final ParkingSlotRepository parkingSlotRepository;

    @Transactional(readOnly = true)
    public List<SlotResponse> getAllSlots() {
        return parkingSlotRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public SlotResponse createSlot(SlotRequest request) {
        if (parkingSlotRepository.existsBySlotNumber(request.getSlotNumber())) {
            throw new DuplicateSlotException("Slot already exists with number: " + request.getSlotNumber());
        }

        ParkingSlot slot = ParkingSlot.builder()
                .slotNumber(request.getSlotNumber())
                .reserved(false)
                .active(true)
                .build();

        ParkingSlot savedSlot = parkingSlotRepository.save(slot);
        return mapToResponse(savedSlot);
    }

    @Transactional
    public SlotResponse updateSlot(Long id, SlotRequest request) {
        ParkingSlot slot = parkingSlotRepository.findById(id)
                .orElseThrow(() -> new SlotNotFoundException("Slot not found with id: " + id));

        if (!slot.getSlotNumber().equals(request.getSlotNumber()) && 
            parkingSlotRepository.existsBySlotNumber(request.getSlotNumber())) {
            throw new DuplicateSlotException("Slot already exists with number: " + request.getSlotNumber());
        }

        slot.setSlotNumber(request.getSlotNumber());
        ParkingSlot updatedSlot = parkingSlotRepository.save(slot);
        return mapToResponse(updatedSlot);
    }

    @Transactional
    public void deactivateSlot(Long id) {
        ParkingSlot slot = parkingSlotRepository.findById(id)
                .orElseThrow(() -> new SlotNotFoundException("Slot not found with id: " + id));

        slot.setActive(false);
        parkingSlotRepository.save(slot);
    }

    @Transactional
    public void enableSlot(Long id) {
        ParkingSlot slot = parkingSlotRepository.findById(id)
                .orElseThrow(() -> new SlotNotFoundException("Slot not found with id: " + id));

        slot.setActive(true);
        parkingSlotRepository.save(slot);
    }

    private SlotResponse mapToResponse(ParkingSlot slot) {
        return SlotResponse.builder()
                .id(slot.getId())
                .slotNumber(slot.getSlotNumber())
                .reserved(slot.isReserved())
                .active(slot.isActive())
                .createdAt(slot.getCreatedAt())
                .updatedAt(slot.getUpdatedAt())
                .build();
    }
}
