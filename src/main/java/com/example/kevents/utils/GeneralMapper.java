package com.example.kevents.utils;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GeneralMapper {

   @Autowired
   private ModelMapper modelMapper;

   public <D, E> E mapToEntity(D dto, Class<E> entityClass) {
      return modelMapper.map(dto, entityClass);
   }

   public <E, D> D mapToDTO(E entity, Class<D> dtoClass) {
      return modelMapper.map(entity, dtoClass);
   }

   public <E, D> E mapToExistingEntity(D dto, E entity) {
      modelMapper.map(dto, entity);
      return entity;
   }

}
