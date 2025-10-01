package com.example.kevents.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class User {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(nullable = false)
   @NotBlank(message = "username cannot be blank")
   private String username;

   @Column(nullable = false, unique = true)
   @NotBlank(message = "email cannot be blank")
   private String email;

   @Column(nullable = false)
   @NotBlank(message = "password cannot be blank")
   private String password;

   @Column(nullable = false)
   @Enumerated(EnumType.STRING)
   private Role role;
}
