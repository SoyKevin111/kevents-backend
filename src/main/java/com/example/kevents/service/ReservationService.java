package com.example.kevents.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kevents.exceptions.ServerInternalError;
import com.example.kevents.model.Event;
import com.example.kevents.model.Reservation;
import com.example.kevents.model.Status;
import com.example.kevents.repository.EventRepository;
import com.example.kevents.repository.ReservationRepository;
import com.example.kevents.validation.ReservationValidation;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReservationService {

   @Autowired
   private EventRepository eventRepository;
   @Autowired
   private ReservationRepository reservationRepository;
   @Autowired
   private ReservationValidation reservationValidation;

   public Reservation create(Reservation reservation) {
      this.reservationValidation.validateCapacity(reservation.getSeats(), reservation.getEvent().getCapacity());
      try {
         return this.reservationRepository.save(reservation);
      } catch (Exception e) {
         log.error(e.getMessage());
         throw new ServerInternalError("Error creating reservation.");
      }
   }

   public List<Reservation> findByUserAuthenticated(String gmail) {
      try {
         return this.reservationRepository.findByAttendeeEmail(gmail);
      } catch (Exception e) {
         log.error(e.getMessage());
         throw new ServerInternalError("Error finding reservations by attendee email.");
      }
   }

   public List<Reservation> findByEventId(Long id) {
      Event event = eventRepository.findById(id)
            .orElseThrow(() -> new ServerInternalError("Event not found for find reservation."));
      return this.reservationRepository.findByEventAndStatusNot(event, Status.CANCELLED);
   }

   public Reservation update(Reservation reservation) {
      this.reservationValidation.validateCapacity(reservation.getSeats(), reservation.getEvent().getCapacity());
      try {
         return this.reservationRepository.save(reservation);
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
      return this.reservationRepository.findById(id)
            .orElseThrow(() -> new ServerInternalError("Error finding reservation."));
   }

}
