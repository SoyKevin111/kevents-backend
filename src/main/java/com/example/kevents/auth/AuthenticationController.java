package com.example.kevents.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.kevents.auth.dto.AuthLoginRequest;
import com.example.kevents.auth.dto.AuthRegisterRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private UserAuthService userAuthService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid AuthRegisterRequest reques) {
        return new ResponseEntity<>(this.userAuthService.createUser(reques), HttpStatus.CREATED);
    }

    @PostMapping("/log-in")
    public ResponseEntity<?> login(@RequestBody @Valid AuthLoginRequest request) {
        return new ResponseEntity<>(this.userAuthService.loginUser(request), HttpStatus.OK);
    }

}
