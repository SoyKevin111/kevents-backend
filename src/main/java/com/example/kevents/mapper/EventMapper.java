package com.example.kevents.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.kevents.dto.request.EventRequest;
import com.example.kevents.model.Event;

@Mapper(componentModel = "spring")
public interface EventMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "organizer", ignore = true)   
    @Mapping(target = "startDate", ignore = true)   
    @Mapping(target = "endDate", ignore = true) 
    Event toEntity(EventRequest dto);


}
