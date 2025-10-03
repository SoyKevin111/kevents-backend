package com.example.kevents.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.example.kevents.exceptions.model.InternalServerErrorException;
import com.example.kevents.model.User;
import com.example.kevents.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class UserValidation {

    private final UserRepository userRepository;

    public void validateEmail(User user) {
        if (this.userRepository.existsByEmail(user.getEmail())) {
            throw new InternalServerErrorException("Email already exists.");
        }
    }

}
