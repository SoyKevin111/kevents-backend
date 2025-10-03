package com.example.kevents.exceptions.model;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends CustomBaseException {

   public ForbiddenException() {
      super("No tienes acceso para este recurso", HttpStatus.FORBIDDEN);
   }

   public ForbiddenException(String message) {
      super(message, HttpStatus.FORBIDDEN);
   }
}
