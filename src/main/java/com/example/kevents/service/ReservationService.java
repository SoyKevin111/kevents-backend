package com.example.kevents.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kevents.exceptions.ServerInternalError;
import com.example.kevents.model.Event;
import com.example.kevents.model.Reservation;
import com.example.kevents.model.Status;
import com.example.kevents.repository.EventRepository;
import com.example.kevents.repository.ReservationRepository;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class ReservationService {


   @Autowired
   private EventRepository eventRepository;

   @Autowired
   private ReservationRepository reservationRepository;

   public Reservation create(Reservation reservation) {

      try {
         return this.reservationRepository.save(reservation);
      } catch (Exception e) {
         log.error(e.getMessage());
         throw new ServerInternalError("Error creating reservation.");
      }
   }

   //Listar reservas del usuario autenticado por email
   public List<Reservation> findByUserAuthenticated(String gmail) {
      try {
         return this.reservationRepository.findAll()
            .stream()
            .filter(r -> r.getAttendee().getEmail().equals(gmail))
            .collect(Collectors.toList());
      } catch (Exception e) {
         log.error(e.getMessage());
         throw new ServerInternalError("Error finding reservations by attendee email.");
      }
   }

   //obtener reservas de un evento por id, solo puede un organizer o admin
   public List<Reservation> findByEventId(Long id) {
      // validacion de roles
//      Set<String> roles = auth.getAuthorities().stream()
//         .map(GrantedAuthority::getAuthority)
//         .collect(Collectors.toSet());
//      if (!roles.contains("ROLE_ADMIN") && !roles.contains("ROLE_ORGANIZER")) {
//         throw new ServerInternalError("User not authorized for find reservation.");
//      }
      Event event = eventRepository.findById(id)
         .orElseThrow(() -> new ServerInternalError("Event not found for find reservation."));
      return this.reservationRepository.findByEventAndStatusNot(event, Status.CANCELLED);
   }

   //actualizar estado y numero de asistentes de una reserva
   public Reservation update(Reservation reservation, Long id) {
      Reservation reservationFind = this.reservationRepository.findById(id)
         .orElseThrow(() -> new ServerInternalError("Reservation not found for update reservation."));
      try {
         reservationFind.setStatus(reservation.getStatus());
         reservationFind.setSeats(reservation.getSeats());
         return this.reservationRepository.save(reservationFind);
      } catch (Exception e) {
         log.error(e.getMessage());
         throw new ServerInternalError("Error updating reservation.");
      }
   }

   public void cancelById(Long id) {
      Reservation reservationFind = this.reservationRepository.findById(id)
         .orElseThrow(() -> new ServerInternalError("Reservation not found for cancel reservation."));
      try {
         reservationFind.setStatus(Status.CANCELLED);
         this.reservationRepository.save(reservationFind);
      } catch (Exception e) {
         log.error(e.getMessage());
         throw new ServerInternalError("Error canceling reservation.");
      }
   }

   public Reservation findById(Long id) {
      return this.reservationRepository.findById(id).orElseThrow(() -> new ServerInternalError("Error finding reservation."));
   }

   

}
