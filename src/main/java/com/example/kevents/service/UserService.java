package com.example.kevents.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.example.kevents.exceptions.model.InternalServerErrorException;
import com.example.kevents.model.User;
import com.example.kevents.repository.UserRepository;
import com.example.kevents.validation.UserValidation;

import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

   private final UserRepository userRepository;
   private final UserValidation userValidation;

   public User create(User user) {
      this.userValidation.validateEmail(user);
      try {
         return this.userRepository.save(user);
      } catch (Exception e) {
         log.error(e.getMessage());
         throw new InternalServerErrorException("Error creating user.");
      }
   }

   public List<User> findAll() {
      try {
         return this.userRepository.findAll();
      } catch (Exception e) {
         log.error(e.getMessage());
         throw new InternalServerErrorException("Error finding users.");
      }
   }

   public User findById(Long id) {
      try {
         return this.userRepository.findById(id).orElseThrow(() -> new InternalServerErrorException("User not found xd."));
      } catch (Exception e) {
         log.error(e.getMessage());
         throw new InternalServerErrorException("Error finding user by id.");
      }
   }

   public User findByEmail(String email) {
      try {
         return this.userRepository.findByEmail(email)
               .orElseThrow(() -> new InternalServerErrorException("User not found for view serie."));
      } catch (Exception e) {
         log.error(e.getMessage());
         throw new InternalServerErrorException("Error finding user by email.");
      }
   }

}
