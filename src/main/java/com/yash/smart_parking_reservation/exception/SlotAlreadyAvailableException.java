package com.yash.smart_parking_reservation.exception;

public class SlotAlreadyAvailableException extends RuntimeException {
    public SlotAlreadyAvailableException(String message) {
        super(message);
    }
}
