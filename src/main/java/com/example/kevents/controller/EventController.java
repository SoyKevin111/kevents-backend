package com.example.kevents.controller;

import com.example.kevents.dto.request.EventRequest;
import com.example.kevents.dto.response.EventResponse;
import com.example.kevents.model.Event;
import com.example.kevents.service.EventService;
import com.example.kevents.service.UserService;
import com.example.kevents.utils.GeneralMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/kevents/events")
public class EventController {

   @Autowired
   private EventService eventService;
   @Autowired
   private UserService userService;
   @Autowired
   private GeneralMapper mapper;

   @PostMapping // solo organizer
   public ResponseEntity<?> createEvent(@Valid @RequestBody EventRequest eventRequest) {
      Event event = this.mapper.mapToEntity(eventRequest, Event.class);
      event.setId(null);
      event.setStartDate(LocalDate.parse(eventRequest.getStartDate()));
      event.setEndDate(LocalDate.parse(eventRequest.getEndDate()));
      try {
         return ResponseEntity.ok().body(this.eventService.create(event, eventRequest.getOrganizerId()));
      } catch (Exception e) {
         return ResponseEntity.badRequest().body(e.getMessage());
      }
   }

   @GetMapping("filter/{filter}/{value}") // filtrar eventos (publico)
   public ResponseEntity<?> findEventsWithFilter(@PathVariable String filter, @PathVariable String value) {
      List<Event> results = new ArrayList<>();
      switch (filter.toLowerCase()) {
         case "date" -> results = this.eventService.findByDate(LocalDate.parse(value));
         case "location" -> results = this.eventService.findByLocation(value);
         case "organizer" ->
            results = this.eventService.findByOrganizer(value); // se le pasara la credencial del usuario autenticado
         default -> ResponseEntity.badRequest();
      }
      if (results.isEmpty()) {
         return ResponseEntity.badRequest().build();
      }
      return ResponseEntity.ok(results
            .stream()
            .map(e -> EventResponse.builder()
                  .title(e.getTitle())
                  .capacity(e.getCapacity())
                  .description(e.getDescription())
                  .location(e.getLocation())
                  .startDate(e.getStartDate().toString())
                  .endDate(e.getEndDate().toString())
                  .organizer(e.getOrganizer().getUsername())
                  .build()));
   }

   @GetMapping("/{id}") // detalles de un evento (publico)
   public ResponseEntity<?> showDetailsById(@PathVariable Long id) {
      try {
         Event event = this.eventService.findById(id);
         return ResponseEntity.ok(EventResponse.builder()
               .title(event.getTitle())
               .capacity(event.getCapacity())
               .description(event.getDescription())
               .location(event.getLocation())
               .startDate(event.getStartDate().toString())
               .endDate(event.getEndDate().toString())
               .organizer(event.getOrganizer().getUsername())
               .build());
      } catch (Exception e) {
         return ResponseEntity.badRequest().body(e.getMessage());
      }
   }

   @PutMapping("/{id}") // solo organizer y admin, validar con authentication el organizador
   public ResponseEntity<?> updateEvent(@RequestBody EventRequest eventRequest, @PathVariable Long id) { // sin valid
                                                                                                         // porque es
                                                                                                         // opcional xd
      Event event = this.mapper.mapToEntity(eventRequest, Event.class);
      event.setId(null);
      if (eventRequest.getOrganizerId() != null) {
         event.setOrganizer(this.userService.findById(eventRequest.getOrganizerId()));
      }
      try {
         return ResponseEntity.ok(this.eventService.update(event, id));
      } catch (Exception e) {
         return ResponseEntity.badRequest().body(e.getMessage());
      }
   }

   @DeleteMapping("/{id}") // solo organizer y admin,
   public ResponseEntity<?> deleteEvent(@PathVariable Long id) {
      try {
         this.eventService.delete(id);
         return ResponseEntity.ok("Event deleted.");
      } catch (Exception e) {
         return ResponseEntity.badRequest().body(e.getMessage());
      }
   }

}
