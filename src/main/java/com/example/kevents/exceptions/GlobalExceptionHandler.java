package com.example.kevents.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.kevents.dto.response.ExceptionErrorResponse;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

@ControllerAdvice
public class GlobalExceptionHandler {

   // Validacion generica
   @ExceptionHandler(ServerInternalError.class)
   public ResponseEntity<ExceptionErrorResponse> handleGeneric(ServerInternalError ex) {
      String error = ex.getError();
      String message = ex.getMessage();

      ExceptionErrorResponse response = ExceptionErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .error((error == null || error.isEmpty()) ? "[Server Internal Error]" : error)
            .message((message == null || message.isEmpty()) ? "Server Internal Error" : message)
            .build();

      return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(response);
   }

   // validacion enum
   @ExceptionHandler(HttpMessageNotReadableException.class)
   public ResponseEntity<ExceptionErrorResponse> handleInvalidEnum(HttpMessageNotReadableException ex) {
      if (ex.getCause() instanceof InvalidFormatException ife) {
         if (ife.getTargetType().isEnum()) {
            ExceptionErrorResponse response = ExceptionErrorResponse.builder()
                  .timestamp(LocalDateTime.now())
                  .status(HttpStatus.BAD_REQUEST.value())
                  .error("[Enum Type]")
                  .message("Tipo de Enum no válido: " + ife.getValue())
                  .build();

            return ResponseEntity
                  .status(HttpStatus.BAD_REQUEST)
                  .body(response);
         }
      }
      return null;
   }
}
