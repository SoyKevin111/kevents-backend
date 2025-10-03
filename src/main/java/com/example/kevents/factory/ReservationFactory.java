package com.example.kevents.factory;

import java.time.LocalDate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.kevents.dto.request.ReservationCreateRequest;
import com.example.kevents.dto.request.ReservationUpdateRequest;
import com.example.kevents.model.Reservation;
import com.example.kevents.service.EventService;
import com.example.kevents.service.ReservationService;
import com.example.kevents.service.UserService;
import com.example.kevents.validation.InputValidation;

@Component
@RequiredArgsConstructor
public class ReservationFactory {

    private final EventService eventService;
    private final UserService userService;
    private final ReservationService reservationService;
    private final InputValidation inputValidation;

    public Reservation forCreate(ReservationCreateRequest dto) {
        Reservation reservation = new Reservation();
        reservation.setSeats(dto.getSeats());
        reservation.setAttendee(userService.findById(dto.getAttendeeId()));
        reservation.setEvent(eventService.findById(dto.getEventId()));
        reservation.setCreatedAt(LocalDate.parse(dto.getCreatedAt()));
        reservation.setStatus(dto.getStatus());
        return reservation;
    }

    public Reservation forUpdate(ReservationUpdateRequest dto, Long id) {
        Reservation reservationFind = this.reservationService.findById(id);
        this.inputValidation.setIfNotNull(dto.getState(), e -> reservationFind.setStatus(e));
        this.inputValidation.setIfNotInteger(dto.getSeats(), e -> reservationFind.setSeats(e));
        return reservationFind;
    }

}
