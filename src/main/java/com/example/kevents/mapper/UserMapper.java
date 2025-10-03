package com.example.kevents.mapper;

import com.example.kevents.dto.request.UserRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.kevents.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    User toEntity(UserRequestDTO dto);


}
