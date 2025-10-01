package com.example.kevents.auth;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.kevents.auth.dto.AuthLoginRequest;
import com.example.kevents.auth.dto.AuthRegisterRequest;
import com.example.kevents.auth.dto.AuthResponse;
import com.example.kevents.model.Role;
import com.example.kevents.model.User;
import com.example.kevents.service.UserService;

@Service
public class UserAuthService implements UserDetailsService {

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthResponse loginUser(AuthLoginRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();
        Authentication authentication = this.authenticate(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtils.createToken(authentication);
        return AuthResponse.builder()
                .email(email)
                .message("usuario cargado")
                .accessToken(accessToken)
                .build();
    }

    public Authentication authenticate(String email, String password) {
        UserDetails userDetails = this.loadUserByUsername(email);
        if (userDetails == null) {
            throw new BadCredentialsException("Invalido username o password");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Password Invalido.");
        }
        return new UsernamePasswordAuthenticationToken(
                email,
                userDetails.getPassword(),
                userDetails.getAuthorities());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByEmail(username);
        List<GrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority("ROLE_" + user.getRole()));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                true, true, true, true,
                authorities);
    }

    public AuthResponse createUser(AuthRegisterRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();

        User user = User.builder()
                .username(request.getUsername())
                .email(email)
                .password(passwordEncoder.encode(password))
                .role(Role.USER)
                .build();

        User userSaved = this.userService.create(user);

        Collection<GrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority("ROLE_".concat(userSaved.getRole().name())));

        Authentication authentication = new UsernamePasswordAuthenticationToken(userSaved.getEmail(), null,
                authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication); // inicia la sesion

        String accessToken = jwtUtils.createToken(authentication);

        return AuthResponse.builder()
                .message("usuario registrado correctamente.")
                .accessToken(accessToken)
                .email(userSaved.getEmail())
                .build();
    }

}
