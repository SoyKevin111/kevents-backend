package com.example.kevents.validation;

import com.example.kevents.exceptions.model.ConflictException;
import org.springframework.stereotype.Component;

import com.example.kevents.exceptions.model.InternalServerErrorException;

@Component
public class ReservationValidation {

   public void validateCapacity(int seats, int capacity) {
      if (seats > capacity) {
         throw new ConflictException("Capacity exceeded seats: " + seats + " <= capacity: " + capacity);
      }
   }

}
