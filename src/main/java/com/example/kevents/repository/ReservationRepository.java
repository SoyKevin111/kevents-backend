package com.example.kevents.repository;

import com.example.kevents.model.Event;
import com.example.kevents.model.Reservation;
import com.example.kevents.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
   List<Reservation> findByEventAndStatusNot(Event event, Status status);
}
