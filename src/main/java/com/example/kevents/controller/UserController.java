package com.example.kevents.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.kevents.dto.request.UserRequest;
import com.example.kevents.model.User;
import com.example.kevents.service.UserService;
import com.example.kevents.utils.GeneralMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/kevents/users")
public class UserController {

   @Autowired
   private UserService userService;
   @Autowired
   private GeneralMapper mapper;

   @PostMapping // solo admin
   public ResponseEntity<?> createUser(@Valid @RequestBody UserRequest userRequest) {
      User user = this.mapper.mapToEntity(userRequest, User.class);
      user.setId(null); // por modelmapper xd
      try {
         return ResponseEntity.ok(this.userService.create(user));
      } catch (Exception e) {
         return ResponseEntity.badRequest().body(e.getMessage());
      }
   }

   @GetMapping // solo admin
   public ResponseEntity<?> findAllUsers() {
      try {
         return ResponseEntity.ok(this.userService.findAll());
      } catch (Exception e) {
         return ResponseEntity.badRequest().body(e.getMessage());
      }
   }

   @GetMapping("/{id}") // ver perfil, solo admin
   public ResponseEntity<?> viewProfile(@PathVariable Long id,
         org.springframework.security.core.Authentication authentication) { // Authorization.getName()
      // validacion
      try {
         return ResponseEntity.ok(this.userService.findByEmail(authentication.getName()));
      } catch (Exception e) {
         return ResponseEntity.badRequest().body(e.getMessage());
      }
   }

}
