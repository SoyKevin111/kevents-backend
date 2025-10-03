package com.example.kevents.dto.request;

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
public class EventCreateRequest {
   @NotBlank(message = "Title is required")
   private String title;
   @NotBlank(message = "Description is required")
   private String description;
   @NotBlank(message = "Location is required")
   private String location;
   @NotNull(message = "Capacity is required")
   private Integer capacity;
   @NotNull(message = "Start Date is required")
   private String startDate;
   @NotNull(message = "End Date is required")
   private String endDate;
   @NotNull(message = "Organizer ID is required")
   private Long organizerId; //ROL ORGANIZER
}
