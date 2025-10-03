package com.example.kevents.exceptions.model;

import org.springframework.http.HttpStatus;

public class ValidationException extends CustomBaseException {
   public ValidationException() {
      super("Error de validación", HttpStatus.UNPROCESSABLE_ENTITY);
   }

   public ValidationException(String message) {
      super(message, HttpStatus.UNPROCESSABLE_ENTITY);
   }
}
