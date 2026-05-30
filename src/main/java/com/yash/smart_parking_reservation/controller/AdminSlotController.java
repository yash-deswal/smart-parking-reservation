package com.yash.smart_parking_reservation.controller;

import com.yash.smart_parking_reservation.dto.SlotRequest;
import com.yash.smart_parking_reservation.dto.SlotResponse;
import com.yash.smart_parking_reservation.service.AdminSlotService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/slots")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminSlotController {

    private final AdminSlotService adminSlotService;

    @GetMapping
    public ResponseEntity<List<SlotResponse>> getAllSlots() {
        return ResponseEntity.ok(adminSlotService.getAllSlots());
    }

    @PostMapping
    public ResponseEntity<SlotResponse> createSlot(@Valid @RequestBody SlotRequest request) {
        return new ResponseEntity<>(adminSlotService.createSlot(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SlotResponse> updateSlot(@PathVariable Long id, @Valid @RequestBody SlotRequest request) {
        return ResponseEntity.ok(adminSlotService.updateSlot(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivateSlot(@PathVariable Long id) {
        adminSlotService.deactivateSlot(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/enable")
    public ResponseEntity<Void> enableSlot(@PathVariable Long id) {
        adminSlotService.enableSlot(id);
        return ResponseEntity.noContent().build();
    }
}
