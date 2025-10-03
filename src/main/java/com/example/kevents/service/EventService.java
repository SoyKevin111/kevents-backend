package com.example.kevents.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.example.kevents.exceptions.model.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.example.kevents.exceptions.model.InternalServerErrorException;
import com.example.kevents.model.Event;
import com.example.kevents.repository.EventRepository;
import com.example.kevents.validation.EventValidation;

import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventService {

   private final EventRepository eventRepository;
   private final EventValidation eventValidation;

   public Event create(Event event) {
      this.eventValidation.validateOverlapsDate(event);
      try {
         return this.eventRepository.save(event);
      } catch (Exception e) {
         log.error(e.getMessage());
         throw new InternalServerErrorException("Error creating event.");
      }
   }

   public Event update(Event event) {
         try {
         return this.eventRepository.save(event);
      } catch (Exception e) {
         throw new InternalServerErrorException("Error updating event.");
      }
   }

   public List<Event> findByDate(LocalDate date) {
      try {
         return this.eventRepository.findAll()
               .stream()
               .filter(e -> !date.isBefore(e.getStartDate()) && !date.isAfter(e.getEndDate()))
               .collect(Collectors.toList());
      } catch (Exception e) {
         log.error(e.getMessage());
         throw new ResourceNotFoundException("Error finding events by date.");
      }
   }

   public List<Event> findByLocation(String location) {
      try {
         return this.eventRepository.findByLocation(location);
      } catch (Exception e) {
         log.error(e.getMessage());
         throw new ResourceNotFoundException("Error finding events.");
      }
   }

   public List<Event> findByOrganizer(String username) {
      try {
         return this.eventRepository.findByOrganizerUsername(username);
      } catch (Exception e) {
         log.error(e.getMessage());
         throw new ResourceNotFoundException("Error finding events.");
      }
   }

   public Event findById(Long id) {
      try {
         return this.eventRepository.findById(id).orElseThrow(() -> new InternalServerErrorException("Event not found."));
      } catch (Exception e) {
         log.error(e.getMessage());
         throw new ResourceNotFoundException("Error finding event by id.");
      }
   }

   public void delete(Long id) {
      try {
         this.eventRepository.deleteById(id);
      } catch (Exception e) {
         log.error(e.getMessage());
         throw new InternalServerErrorException("Error deleting event by id.");
      }
   }

}
