package com.example.kevents.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ServerInternalError extends RuntimeException {
   private String message;
   private String error;

   public ServerInternalError(String message) {
      this.message = message;
   }

   public ServerInternalError(String error, String message) {
      this.error = error;
      this.message = message;

   }

}
