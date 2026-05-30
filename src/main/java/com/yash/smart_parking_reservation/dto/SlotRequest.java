package com.yash.smart_parking_reservation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SlotRequest {
    @NotBlank(message = "Slot number cannot be blank")
    private String slotNumber;
}
