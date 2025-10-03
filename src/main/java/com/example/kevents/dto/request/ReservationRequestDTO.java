package com.example.kevents.dto.request;


import com.example.kevents.dto.validation.onCreate;
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
public class ReservationRequestDTO {

   @NotNull(message = "ID required", groups = onCreate.class)
   private Long eventId;
   @NotNull(message = "ID required", groups = onCreate.class)
   private Long attendeeId;
   @NotNull(message = "Seats required", groups = onCreate.class)
   private Integer seats;
   @NotNull(message = "Status required", groups = onCreate.class)
   private Status status;
   @NotBlank(message = "CreatedAt required", groups = onCreate.class)
   private String createdAt;

}
