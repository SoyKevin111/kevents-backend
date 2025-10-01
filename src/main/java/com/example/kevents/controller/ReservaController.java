package com.example.kevents.controller;

import com.example.kevents.dto.request.ReservationRequest;
import com.example.kevents.dto.request.ReservationUpdateRequest;
import com.example.kevents.model.Reservation;
import com.example.kevents.model.Status;
import com.example.kevents.service.EventService;
import com.example.kevents.service.ReservationService;
import com.example.kevents.service.UserService;
import com.example.kevents.utils.GeneralMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/kevents/reservations")
public class ReservaController {

   @Autowired
   private ReservationService reservationService;
   @Autowired
   private UserService userService;
   @Autowired
   private EventService eventService;
   @Autowired
   private GeneralMapper generalMapper;

   @PostMapping
   public ResponseEntity<?> createReservation(@RequestBody ReservationRequest reservationRequest) {
      Reservation reservation = new Reservation();
      reservation.setSeats(reservationRequest.getSeats());
      reservation.setAttendee(this.userService.findById(reservationRequest.getAttendeeId()));
      reservation.setEvent(this.eventService.findById(reservationRequest.getEventId()));
      reservation.setCreatedAt(LocalDate.parse(reservationRequest.getCreatedAt()));
      reservation.setStatus(Status.valueOf(reservationRequest.getStatus()));
      try {
         return ResponseEntity.ok(this.reservationService.create(reservation));
      } catch (Exception e) {
         return ResponseEntity.badRequest().body(e.getMessage());
      }
   }

   @GetMapping
   public ResponseEntity<?> findAll(Authentication authentication) { // attendee
      return ResponseEntity.ok(this.reservationService.findByUserAuthenticated(authentication.getName()));
   }

   @GetMapping("/event/{eventId}") // permiso: organizer o admin
   public ResponseEntity<?> reservationsFromEvent(@PathVariable Long eventId) {
      try {
         return ResponseEntity.ok(this.reservationService.findByEventId(eventId));
      } catch (Exception e) {
         return ResponseEntity.badRequest().body(e.getMessage());
      }

   }

   @PutMapping("/{id}")
   public ResponseEntity<?> updateReservation(@RequestBody ReservationUpdateRequest request, @PathVariable Long id) { // no
                                                                                                                      // opcional
      Reservation reservation = this.generalMapper.mapToEntity(request, Reservation.class);
      reservation.setStatus(Status.valueOf(request.getState()));
      reservation.setId(null);
      try {
         return ResponseEntity.ok(this.reservationService.update(reservation, id));
      } catch (Exception e) {
         return ResponseEntity.badRequest().build();
      }

   }

   @DeleteMapping("/{id}")
   public ResponseEntity<?> delete(@PathVariable Long id) {
      try {
         this.reservationService.cancelById(id);
         return ResponseEntity.ok().build();
      } catch (Exception e) {
         return ResponseEntity.badRequest().build();
      }
   }

}
