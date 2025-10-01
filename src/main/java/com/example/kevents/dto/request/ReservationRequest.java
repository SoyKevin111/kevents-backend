package com.example.kevents.dto.request;


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
   private int seats;
   private String status;
   private String createdAt;


}
