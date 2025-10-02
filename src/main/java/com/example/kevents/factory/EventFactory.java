package com.example.kevents.factory;

import java.time.LocalDate;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.kevents.dto.request.EventRequest;
import com.example.kevents.mapper.EventMapper;
import com.example.kevents.model.Event;
import com.example.kevents.model.User;
import com.example.kevents.service.EventService;
import com.example.kevents.service.UserService;
import com.example.kevents.validation.EventValidation;
import com.example.kevents.validation.InputValidation;

@Component
@RequiredArgsConstructor
public class EventFactory {

    private final UserService userService;
    private final EventService eventService;
    private final EventMapper eventMapper;
    private final InputValidation inputValidation;
    private final EventValidation eventValidation;


    public Event forCreate(EventRequest dto) {
        Event event = this.eventMapper.toEntity(dto);
        User organizer = this.userService.findById(dto.getOrganizerId());
        event.setOrganizer(organizer);
        event.setStartDate(LocalDate.parse(dto.getStartDate()));
        event.setEndDate(LocalDate.parse(dto.getEndDate()));
        return event;
    }

    public Event forUpdate(EventRequest dto, Long id) {
        Event eventFind = this.eventService.findById(id);
        this.inputValidation.setIfNotBlank(dto.getTitle(), e -> eventFind.setTitle(e));
        this.inputValidation.setIfNotBlank(dto.getDescription(), e -> eventFind.setDescription(e));
        this.inputValidation.setIfNotBlank(dto.getLocation(), e -> eventFind.setLocation(e));
        this.inputValidation.setIfNotBlank(dto.getStartDate(), e -> eventFind.setStartDate(LocalDate.parse(e)));
        this.inputValidation.setIfNotBlank(dto.getEndDate(), e -> eventFind.setEndDate(LocalDate.parse(e)));
        this.inputValidation.setIfNotInteger(dto.getCapacity(), e -> eventFind.setCapacity(e));
        this.inputValidation.setIfNotNull(dto.getOrganizerId(),
                e -> eventFind.setOrganizer(this.userService.findById(dto.getOrganizerId())));
        this.eventValidation.validateBeforeUpdate(dto, eventFind);
        return eventFind;
    }

}
