package com.example.kevents.controller;

import com.example.kevents.dto.validation.onCreate;
import com.example.kevents.model.Reservation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.kevents.dto.request.ReservationRequestDTO;
import com.example.kevents.factory.ReservationFactory;
import com.example.kevents.service.ReservationService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
public class ReservaController {

   private final ReservationService reservationService;
   private final ReservationFactory reservationFactory;

   @PostMapping
   public ResponseEntity<?> createReservation(@Validated(onCreate.class)  @RequestBody ReservationRequestDTO reservationRequestDTO) {
      Reservation reservation = this.reservationService.create(this.reservationFactory.forCreate(reservationRequestDTO));
      URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(reservation.getId()).toUri();
      return ResponseEntity.created(location).body(reservation);
   }

   @GetMapping
   public ResponseEntity<?> getReservationsUserAuth(Authentication authentication) { //! require auth
      return ResponseEntity.ok(this.reservationService.findByUserAuthenticated(authentication.getName()));
   }

   @GetMapping("/event/{eventId}")
   public ResponseEntity<?> getReservationsByEventId(@PathVariable Long eventId) {
      return ResponseEntity.ok(this.reservationService.findByEventId(eventId));
   }

   @PutMapping("/{id}")
   public ResponseEntity<?> updateReservation(@RequestBody ReservationRequestDTO request, @PathVariable Long id) {
         return ResponseEntity.ok(this.reservationService.update(this.reservationFactory.forUpdate(request, id)));
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<?> deleteReservation(@PathVariable Long id) {
         return ResponseEntity.ok().build();
   }

}
