package com.example.kevents.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.example.kevents.auth.UserAuthService;
import com.example.kevents.auth.jwt.JwtAuthenticationFilter;
import com.example.kevents.auth.jwt.JwtUtils;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Profile("prod")
public class HttpSecurityConfig {

    @Autowired
    AuthenticationConfiguration authenticationConfiguration; //
    @Autowired
    JwtUtils jwtUtils;

    @Bean
    public AuthenticationProvider authenticationProvider(UserAuthService userAuthService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder()); // metodo de codificacion
        provider.setUserDetailsService(userAuthService);
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private HttpSecurity applyCommonConfig(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtils), BasicAuthenticationFilter.class);
    }

    // general para login
    @Bean
    @Order(1)
    public SecurityFilterChain authSecurity(HttpSecurity http) throws Exception {
        applyCommonConfig(http);
        return http
                .securityMatcher("/auth/**")
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(HttpMethod.POST, "/auth/log-in", "/auth/register").permitAll();
                    auth.anyRequest().denyAll();
                })
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtils), BasicAuthenticationFilter.class)
                .build();
    }

    // restricciones para users
    @Bean
    @Order(2)
    public SecurityFilterChain userSecurity(HttpSecurity http) throws Exception {
        applyCommonConfig(http);
        return http
                .securityMatcher("/users/**")
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(HttpMethod.POST, "/users/**").hasRole("ADMIN");
                    auth.requestMatchers(HttpMethod.GET, "/users/**").hasRole("ADMIN");
                    auth.requestMatchers(HttpMethod.GET, "/users/**").hasRole("ADMIN");
                })
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtils), BasicAuthenticationFilter.class)
                .build();
    }

    // restricciones para events
    @Bean
    @Order(3)
    public SecurityFilterChain eventsSecurity(HttpSecurity http) throws Exception {
        applyCommonConfig(http);
        return http
                .securityMatcher("/events/**")
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(HttpMethod.POST, "/events/**").hasRole("ORGANIZER");
                    auth.requestMatchers(HttpMethod.GET, "/events/**").permitAll(); // user or all
                    auth.requestMatchers(HttpMethod.GET, "/filter/**").permitAll();
                    auth.requestMatchers(HttpMethod.PUT, "/events/**").hasAnyRole("ORGANIZER", "ADMIN");
                    auth.requestMatchers(HttpMethod.DELETE, "/events/**").hasAnyRole("ORGANIZER", "ADMIN");
                })
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtils), BasicAuthenticationFilter.class)
                .build();
    }

    @Bean
    @Order(4)
    public SecurityFilterChain reservationsSecurity(HttpSecurity http) throws Exception {
        applyCommonConfig(http);
        return http
                .securityMatcher("/reservations/**")
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(HttpMethod.POST, "/reservations/**").hasRole("ATTENDEE");
                    auth.requestMatchers(HttpMethod.GET, "/reservations/**").hasRole("ATTENDEE");
                    auth.requestMatchers(HttpMethod.GET, "/reservations/event/**").hasAnyRole("ORGANIZER",
                            "ADMIN");
                    auth.requestMatchers(HttpMethod.PUT, "/reservations/**").hasAnyRole("ATTENDEE", "ADMIN");
                    auth.requestMatchers(HttpMethod.DELETE, "/reservations/**").hasAnyRole("ADMIN");

                })
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtils), BasicAuthenticationFilter.class)
                .build();
    }

}
