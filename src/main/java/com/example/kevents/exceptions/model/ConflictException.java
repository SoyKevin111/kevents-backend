package com.example.kevents.exceptions.model;

import org.springframework.http.HttpStatus;

public class ConflictException extends CustomBaseException {
   public ConflictException() {
      super("Conflicto en la operación", HttpStatus.CONFLICT);
   }

   public ConflictException(String message) {
      super(message, HttpStatus.CONFLICT);
   }
}
