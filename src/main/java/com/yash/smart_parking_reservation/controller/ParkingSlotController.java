package com.yash.smart_parking_reservation.controller;

import com.yash.smart_parking_reservation.entity.ParkingSlot;
import com.yash.smart_parking_reservation.service.ParkingSlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/slots")
@RequiredArgsConstructor
public class ParkingSlotController {

    private final ParkingSlotService parkingSlotService;

    @GetMapping
    public ResponseEntity<List<ParkingSlot>> getAllSlots() {
        return ResponseEntity.ok(parkingSlotService.getAllSlots());
    }

    @PostMapping("/{id}/reserve")
    public ResponseEntity<ParkingSlot> reserveSlot(@PathVariable Long id) {
        ParkingSlot reservedSlot = parkingSlotService.reserveSlot(id);
        return ResponseEntity.ok(reservedSlot);
    }

    @PostMapping("/{id}/release")
    public ResponseEntity<ParkingSlot> releaseSlot(@PathVariable Long id) {
        ParkingSlot releasedSlot = parkingSlotService.releaseSlot(id);
        return ResponseEntity.ok(releasedSlot);
    }
}
