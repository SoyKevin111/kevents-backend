package com.example.kevents.validation;

import java.time.LocalDate;

import com.example.kevents.dto.request.EventRequestDTO;
import com.example.kevents.exceptions.model.ConflictException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.kevents.exceptions.model.InternalServerErrorException;
import com.example.kevents.model.Event;
import com.example.kevents.repository.EventRepository;

@Component
@RequiredArgsConstructor
public class EventValidation {

    private final EventRepository eventRepository;

    public void validateOverlapsDate(Event newEvent) {
        System.out.println(newEvent);
        this.eventRepository.findAll()
                .forEach(e -> {
                    if (!newEvent.getEndDate().isBefore(e.getStartDate()) &&
                            !newEvent.getStartDate().isAfter(e.getEndDate())) {
                        throw new ConflictException("Error, date overlaps with event: " + e.getTitle());
                    }
                });
        ;
    }

    public void validateBeforeUpdate(EventRequestDTO dto, Event event) {
        LocalDate inDto = LocalDate.parse(dto.getStartDate());
        LocalDate outDto = LocalDate.parse(dto.getEndDate());
        System.out.println(dto);
        System.out.println(event);
        if (!inDto.equals(event.getStartDate()) || !outDto.equals(event.getEndDate())) {
            this.validateOverlapsDate(event);
        }
    }

}
