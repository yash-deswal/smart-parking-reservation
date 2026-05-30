package com.yash.smart_parking_reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SlotResponse {
    private Long id;
    private String slotNumber;
    private boolean reserved;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @com.fasterxml.jackson.annotation.JsonProperty("isAvailable")
    public boolean getIsAvailable() {
        return !reserved && active;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("isDisabled")
    public boolean getIsDisabled() {
        return !active;
    }
}
