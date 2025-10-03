package com.example.kevents.exceptions.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExceptionErrorResponse {
   private LocalDateTime timestamp;
   private int status;
   private String error;
   private String message;
   private List<FieldErrorDTO> errors;

   public ExceptionErrorResponse(
      LocalDateTime timestamp,
      int status,
      String error
   ) {
      this.timestamp = timestamp;
      this.status = status;
      this.error = error;
   }
}