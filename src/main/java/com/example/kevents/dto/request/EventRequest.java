package com.example.kevents.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventRequest {
   private String title;
   private String description;
   private String location;
   private Integer capacity;
   private String startDate;
   private String endDate;
   private Long organizerId; //ROL ORGANIZER
}
