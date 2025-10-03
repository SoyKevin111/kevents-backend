package com.example.kevents.controller;

import com.example.kevents.dto.request.UserRequestDTO;
import com.example.kevents.dto.validation.onCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.kevents.mapper.UserMapper;
import com.example.kevents.model.User;
import com.example.kevents.service.UserService;

import jakarta.validation.Valid;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

   private final UserService userService;
   private final UserMapper userMapper;

   @PostMapping
   public ResponseEntity<?> createUser(@Validated(onCreate.class) @RequestBody UserRequestDTO dto) {
      User user = this.userMapper.toEntity(dto);
      URI location = ServletUriComponentsBuilder
         .fromCurrentRequest()
         .path("/{id}")
         .buildAndExpand(user.getId())
         .toUri();
      return ResponseEntity.created(location).body(this.userService.create(user));
   }

   @GetMapping
   public ResponseEntity<?> getUsers() {
      return ResponseEntity.ok(this.userService.findAll());
   }

   @GetMapping("/{id}")
   public ResponseEntity<?> getProfile(@PathVariable Long id, org.springframework.security.core.Authentication authentication) {   //! require auth
      return ResponseEntity.ok(this.userService.findByEmail(authentication.getName()));
   }

}
