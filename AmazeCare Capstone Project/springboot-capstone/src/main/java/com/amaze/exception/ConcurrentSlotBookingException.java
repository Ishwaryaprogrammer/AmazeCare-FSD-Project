package com.amaze.exception;

public class ConcurrentSlotBookingException extends RuntimeException {
    public ConcurrentSlotBookingException(String message) {
        super(message);
    }
}
