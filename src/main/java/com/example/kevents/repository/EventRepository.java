package com.example.kevents.repository;

import com.example.kevents.model.Event;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    
    List<Event> findByOrganizerUsername(String username);
    List<Event> findByLocation(String location);
    
    
}
