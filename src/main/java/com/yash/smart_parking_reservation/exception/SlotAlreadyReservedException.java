package com.yash.smart_parking_reservation.exception;

public class SlotAlreadyReservedException extends RuntimeException {
    public SlotAlreadyReservedException(String message) {
        super(message);
    }
}
