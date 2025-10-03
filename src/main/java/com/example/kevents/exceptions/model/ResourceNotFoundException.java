package com.example.kevents.exceptions.model;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends CustomBaseException {
   public ResourceNotFoundException() {
      super("Recurso no encontrado", HttpStatus.NOT_FOUND);
   }

   public ResourceNotFoundException(String message) {
      super(message, HttpStatus.NOT_FOUND);
   }
}
