package com.example.kevents.dto.request;

import com.example.kevents.dto.validation.onCreate;
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
public class EventRequestDTO {
   @NotBlank(message = "Title is required", groups = onCreate.class)
   private String title;
   @NotBlank(message = "Description is required", groups = onCreate.class)
   private String description;
   @NotBlank(message = "Location is required", groups = onCreate.class)
   private String location;
   @NotNull(message = "Capacity is required", groups = onCreate.class)
   private Integer capacity;
   @NotNull(message = "Start Date is required", groups = onCreate.class)
   private String startDate;
   @NotNull(message = "End Date is required", groups = onCreate.class)
   private String endDate;
   @NotNull(message = "Organizer ID is required", groups = onCreate.class)
   private Long organizerId; //ROL ORGANIZER
}
