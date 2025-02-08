package com.dg.security;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    /*
     * Visit http://localhost:8080/actuator/health → Should require login
     * Visit http://localhost:8080/actuator/info → Should be public
     * Visit http://localhost:8080/actuator/env → Should require login
     * Username: admin
     * Password: adminpassword
     */


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(EndpointRequest.to(//"health",
                                "info")).permitAll() // Public endpoints
                        .requestMatchers(EndpointRequest.toAnyEndpoint()).hasRole("ADMIN") // Secure other Actuator endpoints
                        .anyRequest().authenticated() // Secure all other requests
                )
                .formLogin(login -> login.disable()) // Disable form-based login
                .httpBasic(Customizer.withDefaults()); // Modern way to enable HTTP Basic Auth

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("user")
                .password("{noop}password") // {noop} means no password encoding
                .roles("USER")
                .build();

        UserDetails admin = User.withUsername("admin")
                .password("{noop}adminpassword")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }
}