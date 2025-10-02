package com.example.kevents.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.kevents.dto.request.UserRequest;
import com.example.kevents.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    User toEntity(UserRequest dto);

}
