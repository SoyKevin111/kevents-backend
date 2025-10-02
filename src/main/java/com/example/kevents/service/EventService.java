package com.example.kevents.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kevents.exceptions.ServerInternalError;
import com.example.kevents.model.Event;
import com.example.kevents.repository.EventRepository;
import com.example.kevents.validation.EventValidation;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EventService {

   @Autowired
   private EventRepository eventRepository;
   @Autowired
   private EventValidation eventValidation;

   public Event create(Event event) {
      this.eventValidation.validateOverlapsDate(event);
      try {
         return this.eventRepository.save(event);
      } catch (Exception e) {
         log.error(e.getMessage());
         throw new ServerInternalError("Error creating event.");
      }
   }

   public Event update(Event event) {
         try {
         return this.eventRepository.save(event);
      } catch (Exception e) {
         throw new ServerInternalError("Error updating event.");
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
         throw new ServerInternalError("Error finding events by date.");
      }
   }

   public List<Event> findByLocation(String location) {
      try {
         return this.eventRepository.findByLocation(location);
      } catch (Exception e) {
         log.error(e.getMessage());
         throw new ServerInternalError("Error finding events.");
      }
   }

   public List<Event> findByOrganizer(String username) {
      try {
         return this.eventRepository.findByOrganizerUsername(username);
      } catch (Exception e) {
         log.error(e.getMessage());
         throw new ServerInternalError("Error finding events.");
      }
   }

   public Event findById(Long id) {
      try {
         return this.eventRepository.findById(id).orElseThrow(() -> new ServerInternalError("Event not found."));
      } catch (Exception e) {
         log.error(e.getMessage());
         throw new ServerInternalError("Error finding event by id.");
      }
   }

   public void delete(Long id) {
      try {
         this.eventRepository.deleteById(id);
      } catch (Exception e) {
         log.error(e.getMessage());
         throw new ServerInternalError("Error deleting event by id.");
      }
   }

}
