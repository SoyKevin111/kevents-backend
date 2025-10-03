package com.example.kevents.mapper;

import com.example.kevents.dto.request.UserCreateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.kevents.dto.request.UserUpdateRequest;
import com.example.kevents.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    User toEntity(UserCreateRequest dto);

    @Mapping(target = "id", ignore = true)
    User toEntity(UserUpdateRequest dto);

}
