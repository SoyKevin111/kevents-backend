package com.example.kevents.controller;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.kevents.dto.request.EventCreateRequest;
import com.example.kevents.dto.request.EventUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.kevents.dto.response.EventResponse;
import com.example.kevents.factory.EventFactory;
import com.example.kevents.model.Event;
import com.example.kevents.service.EventService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventController {

   private final EventService eventService;
   private final EventFactory eventFactory;

   @PostMapping
   public ResponseEntity<?> createEvent(@Valid @RequestBody EventCreateRequest dto) {
      Event event = this.eventService.create(this.eventFactory.forCreate(dto));
      URI location = ServletUriComponentsBuilder
         .fromCurrentRequest()
         .path("/{id}")
         .buildAndExpand(event.getId())
         .toUri();
      return ResponseEntity.created(location).body(event);
   }

   @GetMapping("filter/{filter}/{value}") // filtrar eventos (publico)
   public ResponseEntity<?> getEventsByFilter(@PathVariable String filter, @PathVariable String value) {
      List<Event> results = new ArrayList<>();
      switch (filter.toLowerCase()) {
         case "date" -> results = this.eventService.findByDate(LocalDate.parse(value));
         case "location" -> results = this.eventService.findByLocation(value);
         case "organizer" -> results = this.eventService.findByOrganizer(value);
         default -> ResponseEntity.badRequest().build();
      }
      if (results.isEmpty()) {
         return ResponseEntity.notFound().build();
      }
      return ResponseEntity.ok(results.stream()
         .map(e -> EventResponse.builder()
            .title(e.getTitle())
            .capacity(e.getCapacity())
            .description(e.getDescription())
            .location(e.getLocation())
            .startDate(e.getStartDate().toString())
            .endDate(e.getEndDate().toString())
            .organizer(e.getOrganizer().getUsername())
            .build())
         .collect(Collectors.toList()));
   }

   @GetMapping("/{id}") // detalles de un evento (publico)
   public ResponseEntity<?> getEventById(@PathVariable Long id) {
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

   @PutMapping("/{id}")
   public ResponseEntity<?> updateEvent(@RequestBody EventUpdateRequest dto, @PathVariable Long id) {
      Event event = this.eventFactory.forUpdate(dto, id);
      return ResponseEntity.ok(this.eventService.update(event));
   }

   @DeleteMapping("/{id}") // solo organizer y admin,
   public ResponseEntity<?> deleteEvent(@PathVariable Long id) {
      this.eventService.delete(id);
      return ResponseEntity.ok().build();
   }

}
