package com.example.kevents.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
public class EventResponse {

   private String title;
   private String description;
   private String location;
   private int capacity;
   private String startDate;
   private String endDate;
   private String organizer;
}
