package com.example.notification.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for testing purposes
                .authorizeHttpRequests(auth -> {
                    // Allow unauthenticated access to specific API endpoints
                    auth.requestMatchers("/notifications/**").permitAll();  // Ensure this covers other endpoints

                    // Require authentication for any other request
                    auth.anyRequest().authenticated();
                })
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(exceptions -> {
                    exceptions.accessDeniedHandler((request, response, accessDeniedException) -> {
                        System.out.println("Access Denied: " + accessDeniedException.getMessage());
                        response.sendError(403, "Access Denied: " + accessDeniedException.getMessage());
                    });
                    exceptions.authenticationEntryPoint((request, response, authException) -> {
                        System.out.println("Authentication Required: " + authException.getMessage());
                        response.sendError(401, "Authentication Required: " + authException.getMessage());
                    });
                })
                .httpBasic(httpBasic -> httpBasic.disable());  // Disable HTTP Basic authentication

        return http.build();
    }
}

