package com.example.kevents.exceptions.model;

import org.springframework.http.HttpStatus;

public abstract class CustomBaseException extends RuntimeException {

   private final HttpStatus status;

   public CustomBaseException(String message, HttpStatus status) {
      super(message);
      this.status = status;
   }

   public HttpStatus getStatus() {
      return status;
   }
}

