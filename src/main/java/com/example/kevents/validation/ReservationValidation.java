package com.example.kevents.validation;

import org.springframework.stereotype.Component;

import com.example.kevents.exceptions.ServerInternalError;

@Component
public class ReservationValidation {
    
    public void validateCapacity(int seats, int capacity) {
        if (seats > capacity) {
            throw new ServerInternalError("seats > capacity");
        }
    }

}
