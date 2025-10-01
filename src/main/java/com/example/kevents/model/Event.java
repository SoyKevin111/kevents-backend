package com.example.kevents.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "events")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Event {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(nullable = false)
   @NotBlank(message = "title cannot be blank")
   private String title;

   @Column(nullable = false)
   @NotBlank(message = "description cannot be blank")
   private String description;

   @Column(nullable = false)
   @NotBlank(message = "location cannot be blank")
   private String location;

   @Column(nullable = false)
   private int capacity;

   @Column(nullable = false)
   private LocalDate startDate;

   @Column(nullable = false)
   private LocalDate endDate;

   @ManyToOne
   @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
   private User organizer; //ROL ORGANIZER
}
