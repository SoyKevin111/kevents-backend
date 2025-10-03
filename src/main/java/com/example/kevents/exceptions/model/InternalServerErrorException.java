package com.example.kevents.exceptions.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class InternalServerErrorException extends CustomBaseException {
   public InternalServerErrorException() {
      super("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR);
   }

   public InternalServerErrorException(String message) {
      super(message, HttpStatus.INTERNAL_SERVER_ERROR);
   }

}
