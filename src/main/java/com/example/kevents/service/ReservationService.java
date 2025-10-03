package com.example.kevents.service;

import java.util.List;

import com.example.kevents.exceptions.model.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.example.kevents.exceptions.model.InternalServerErrorException;
import com.example.kevents.model.Event;
import com.example.kevents.model.Reservation;
import com.example.kevents.model.Status;
import com.example.kevents.repository.EventRepository;
import com.example.kevents.repository.ReservationRepository;
import com.example.kevents.validation.ReservationValidation;

import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationService {

   private final EventRepository eventRepository;
   private final ReservationRepository reservationRepository;
   private final ReservationValidation reservationValidation;

   public Reservation create(Reservation reservation) {
      this.reservationValidation.validateCapacity(reservation.getSeats(), reservation.getEvent().getCapacity());
      try {
         return this.reservationRepository.save(reservation);
      } catch (Exception e) {
         log.error(e.getMessage());
         throw new InternalServerErrorException("Error creating reservation.");
      }
   }

   public List<Reservation> findByUserAuthenticated(String gmail) {
      try {
         return this.reservationRepository.findByAttendeeEmail(gmail);
      } catch (Exception e) {
         log.error(e.getMessage());
         throw new ResourceNotFoundException("Error finding reservations by attendee email.");
      }
   }

   public List<Reservation> findByEventId(Long id) {
      Event event = eventRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Event not found for find reservation."));
      return this.reservationRepository.findByEventAndStatusNot(event, Status.CANCELLED);
   }

   public Reservation update(Reservation reservation) {
      this.reservationValidation.validateCapacity(reservation.getSeats(), reservation.getEvent().getCapacity());
      try {
         return this.reservationRepository.save(reservation);
      } catch (Exception e) {
         log.error(e.getMessage());
         throw new InternalServerErrorException("Error updating reservation.");
      }
   }

   public void cancelById(Long id) {
      Reservation reservationFind = this.reservationRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Reservation not found for cancel reservation."));
      try {
         reservationFind.setStatus(Status.CANCELLED);
         this.reservationRepository.save(reservationFind);
      } catch (Exception e) {
         log.error(e.getMessage());
         throw new InternalServerErrorException("Error canceling reservation.");
      }
   }

   public Reservation findById(Long id) {
      return this.reservationRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Error finding reservation."));
   }

}
