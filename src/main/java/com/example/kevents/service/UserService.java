package com.example.kevents.service;

import com.example.kevents.exceptions.ServerInternalError;
import com.example.kevents.model.User;
import com.example.kevents.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserService {

   @Autowired
   private UserRepository userRepository;

   public User create(User user) {
      try {
         return this.userRepository.save(user);
      } catch (Exception e) {
         log.error(e.getMessage());
         throw new ServerInternalError("Error creating user.");
      }
   }

   public List<User> findAll() {
      try {
         return this.userRepository.findAll();
      } catch (Exception e) {
         log.error(e.getMessage());
         throw new ServerInternalError("Error finding users.");
      }
   }


   public User findById(Long id) {
      try {
         return this.userRepository.findById(id).orElseThrow(() -> new ServerInternalError("User not found xd."));
      } catch (Exception e) {
         log.error(e.getMessage());
         throw new ServerInternalError("Error finding user by id.");
      }
   }

   public User findByEmail(String email) {
      try {
         return this.userRepository.findByEmail(email).orElseThrow(() -> new ServerInternalError("User not found for view serie."));
      } catch (Exception e) {
         log.error(e.getMessage());
         throw new ServerInternalError("Error finding user by email.");
      }
   }

}
