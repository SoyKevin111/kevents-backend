package com.example.kevents.exceptions;

import java.time.LocalDateTime;
import java.util.List;

import com.example.kevents.exceptions.dto.FieldErrorDTO;
import com.example.kevents.exceptions.model.CustomBaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.kevents.exceptions.dto.ExceptionErrorResponse;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

@ControllerAdvice
public class GlobalExceptionHandler {

   //Validacion para excepciones personalizadas
   @ExceptionHandler(CustomBaseException.class)
   public ResponseEntity<ExceptionErrorResponse> handleCustomExceptions(CustomBaseException ex) {
      ExceptionErrorResponse response = ExceptionErrorResponse.builder()
         .timestamp(LocalDateTime.now())
         .status(ex.getStatus().value())
         .error("[" + ex.getStatus().getReasonPhrase() + "]")
         .message(ex.getMessage())
         .build();

      return ResponseEntity.status(ex.getStatus()).body(response);
   }


   //Validaciones de propiedades con @Valid
   @ExceptionHandler(MethodArgumentNotValidException.class)
   public ResponseEntity<ExceptionErrorResponse> handleProperty(
      MethodArgumentNotValidException ex
   ) {
      List<FieldErrorDTO> errors = ex.getBindingResult().getFieldErrors()
         .stream()
         .map(err -> new FieldErrorDTO(err.getField(), err.getDefaultMessage()))
         .toList();

      ExceptionErrorResponse response = ExceptionErrorResponse.builder()
         .timestamp(LocalDateTime.now())
         .status(HttpStatus.BAD_REQUEST.value())
         .error("[Request Error]")
         .errors(errors)
         .build();

      return ResponseEntity
         .status(HttpStatus.BAD_REQUEST)
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
