package com.example.kevents.exceptions.model;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends CustomBaseException {

   public UnauthorizedException() {
      super("No estás autenticado", HttpStatus.UNAUTHORIZED);
   }

   public UnauthorizedException(String message) {
      super(message, HttpStatus.UNAUTHORIZED);
   }
}
