package com.example.kevents.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.kevents.dto.request.ReservationRequest;
import com.example.kevents.dto.request.ReservationUpdateRequest;
import com.example.kevents.factory.ReservationFactory;
import com.example.kevents.service.ReservationService;

@RestController
@RequestMapping("/kevents/reservations")
public class ReservaController {

   @Autowired
   private ReservationService reservationService;
   @Autowired
   private ReservationFactory reservationFactory;

   @PostMapping
   public ResponseEntity<?> createReservation(@RequestBody ReservationRequest reservationRequest) {
      try {
         return ResponseEntity
               .ok(this.reservationService.create(this.reservationFactory.forCreate(reservationRequest)));
      } catch (Exception e) {
         return ResponseEntity.badRequest().body(e.getMessage());
      }
   }

   @GetMapping
   public ResponseEntity<?> reservationUserAuth(Authentication authentication) { //! require auth
      return ResponseEntity.ok(this.reservationService.findByUserAuthenticated(authentication.getName()));
   }

   @GetMapping("/event/{eventId}")
   public ResponseEntity<?> reservationsFromEvent(@PathVariable Long eventId) {
      try {
         return ResponseEntity.ok(this.reservationService.findByEventId(eventId));
      } catch (Exception e) {
         return ResponseEntity.badRequest().body(e.getMessage());
      }

   }

   @PutMapping("/{id}")
   public ResponseEntity<?> updateReservation(@RequestBody ReservationUpdateRequest request, @PathVariable Long id) {
      try {
         return ResponseEntity.ok(this.reservationService.update(this.reservationFactory.forUpdate(request, id)));
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
