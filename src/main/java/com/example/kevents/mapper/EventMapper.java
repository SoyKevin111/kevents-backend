package com.example.kevents.mapper;

import com.example.kevents.dto.request.EventRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.kevents.model.Event;

@Mapper(componentModel = "spring")
public interface EventMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "organizer", ignore = true)   
    @Mapping(target = "startDate", ignore = true)   
    @Mapping(target = "endDate", ignore = true) 
    Event toEntity(EventRequestDTO dto);



}
