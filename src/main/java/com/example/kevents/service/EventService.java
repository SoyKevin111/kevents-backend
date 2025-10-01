package com.example.kevents.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kevents.exceptions.ServerInternalError;
import com.example.kevents.model.Event;
import com.example.kevents.model.User;
import com.example.kevents.repository.EventRepository;
import com.example.kevents.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class EventService {

   @Autowired
   private EventRepository eventRepository;
   @Autowired
   private UserRepository userRepository;


   public Event create(Event event, Long idOrganizer) {
      User organizer = this.userRepository.findById(idOrganizer)
         .orElseThrow(
            () -> new ServerInternalError("Organizer not found for create event."));
      event.setOrganizer(organizer);
      try {
         return this.eventRepository.save(event);
      } catch (Exception e) {
         log.error(e.getMessage());
         throw new ServerInternalError("Error creating event.");
      }
   }

   public Event update(Event event, Long id) {
      Event eventFind = this.eventRepository.findById(id).
         orElseThrow(() -> new ServerInternalError("Event not found."));

      eventFind.setTitle(event.getTitle() == null || event.getTitle().isBlank() ? eventFind.getTitle() : event.getTitle());
      eventFind.setDescription(event.getDescription() == null || event.getDescription().isBlank() ? eventFind.getDescription() : event.getDescription());
      eventFind.setCapacity(event.getCapacity() <= 0 ? eventFind.getCapacity() : event.getCapacity());
      eventFind.setStartDate(event.getStartDate() == null ? eventFind.getStartDate() : event.getStartDate());
      eventFind.setEndDate(event.getEndDate() == null ? eventFind.getEndDate() : event.getEndDate());
      eventFind.setLocation(event.getLocation() == null || event.getLocation().isBlank() ? eventFind.getLocation() : event.getLocation());
      eventFind.setOrganizer(event.getOrganizer() == null ? eventFind.getOrganizer() : event.getOrganizer());

      System.out.println(event);
      System.out.println(eventFind);
      try {
         return this.eventRepository.save(eventFind);
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
         return this.eventRepository.findAll()
            .stream()
            .filter(e -> e.getLocation().equalsIgnoreCase(location))
            .collect(Collectors.toList());
      } catch (Exception e) {
         log.error(e.getMessage());
         throw new ServerInternalError("Error finding events.");
      }
   }

   public List<Event> findByOrganizer(String organizer) {
      try {
         return this.eventRepository.findAll()
            .stream()
            .filter(e -> e.getOrganizer().getUsername().equals(organizer))
            .collect(Collectors.toList());
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
