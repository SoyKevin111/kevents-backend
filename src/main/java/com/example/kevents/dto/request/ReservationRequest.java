package com.example.kevents.dto.request;


import com.example.kevents.model.Status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRequest {

   private Long eventId;
   private Long attendeeId;
   private Integer seats;
   private Status status;
   private String createdAt;


}
