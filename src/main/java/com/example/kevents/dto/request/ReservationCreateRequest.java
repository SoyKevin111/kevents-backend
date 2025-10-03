package com.example.kevents.dto.request;


import com.example.kevents.model.Status;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationCreateRequest {

   @NotNull(message = "ID required")
   private Long eventId;
   @NotNull(message = "ID required")
   private Long attendeeId;
   @NotNull(message = "Seats required")
   private Integer seats;
   @NotNull(message = "Status required")
   private Status status;
   @NotBlank(message = "CreatedAt required")
   private String createdAt;

}
