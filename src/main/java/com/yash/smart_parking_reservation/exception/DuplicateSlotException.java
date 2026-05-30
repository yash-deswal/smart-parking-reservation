package com.yash.smart_parking_reservation.exception;

public class DuplicateSlotException extends RuntimeException {
    public DuplicateSlotException(String message) {
        super(message);
    }
}
