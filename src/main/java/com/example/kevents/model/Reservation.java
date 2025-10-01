package com.example.kevents.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reservations")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {


   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @ManyToOne
   @JoinColumn(name = "event_id", referencedColumnName = "id", nullable = false)
   private Event event;

   @ManyToOne
   @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
   private User attendee;

   @Column(nullable = false)
   private int seats;

   @Column(nullable = false)
   @Enumerated(EnumType.STRING)
   private Status status;

   @Column(nullable = false)
   private LocalDate createdAt;
}
